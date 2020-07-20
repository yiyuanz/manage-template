/*
 * www.cnvex.cn Inc.
 * Copyright (c) 2016 All Rights Reserved.
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                   O\  =  /O
 *                ____/`---'\____
 *              .'  \\|     |//  `.
 *             /  \\|||  :  |||//  \
 *            /  _||||| -:- |||||-  \
 *            |   | \\\  -  /// |   |
 *            | \_|  ''\---/''  |   |
 *            \  .-\__  `-`  ___/-. /
 *          ___`. .'  /--.--\  `. . __
 *       ."" '<  `.___\_<|>_/___.'  >'"".
 *      | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *      \  \ `-.   \_ __\ /__ _/   .-` /  /
 *  ======`-.____`-.___\_____/___.-`____.-'======
 *                     `=---='
 *  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *           佛祖保佑       永无BUG
 */

/*
 * 修订记录：
 * zyiyuan 2:18:44 PM 创建
 */
package com.ruoyi.zxydk.filterchain.invoke;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Lists;
import com.ruoyi.zxydk.common.transaction.ZxydkTransactionTemplate;
import com.ruoyi.zxydk.filterchain.interfaces.FilterCommandChain;
import com.ruoyi.zxydk.filterchain.interfaces.Filtercommand;

import io.jsonwebtoken.lang.Assert;


@SuppressWarnings("all")
public class FilterCommondChainInvoke<R extends Object> implements FilterCommandChain<R> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -192765737729108189L;
	
	// 日志
	protected static final Logger logger = LoggerFactory.getLogger(FilterCommondChainInvoke.class);
	
	// command chain
	private Iterator<Filtercommand<R>> commands;
	
	// spring transaction template
	protected TransactionTemplate template;
	
	//  golbal transaction
	protected Boolean hasGlobalTransAction = Boolean.FALSE;

	@Override
	public void execute(R object, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		if( this.hasGlobalTransAction ) {
			Assert.notNull(this.template , " 无法发起责任链模式，因为开启了全局事务，事务模板不能为空！"); 
			new ZxydkTransactionTemplate<Void>(template) {
				@Override
				protected Void justDoIt() {
					// TODO Auto-generated method stub
					process( object, vals );
					return null;
				}
			}.template();
		}else {
			process( object, vals ); 
		}
	}

	@Override
	public void process(R object, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		if( this.commands.hasNext() ) {
			// step 1 : get executeAble command
			Filtercommand<R> exeCommand = this.commands.next();
			// step 2 : check has execute process
			if( exeCommand.match(object, vals) ) {
				/** command matched is true */ 
				if( !this.hasGlobalTransAction && exeCommand.hasOpenTrAnsaction() ) {
					/** 全局事务关闭同时，子命令行事务开启 */ 
					new ZxydkTransactionTemplate<Void>(template) {
						@Override
						protected Void justDoIt() {
							// TODO Auto-generated method stub
							exeCommand.execute(object, vals);
							return null;
						}
					}.template();
				}else {
					/** 已有全局事务开关 或者 全局无事务 */ 
					exeCommand.execute(object, vals);
				}
			}
			// step 3 : push chain
			exeCommand.transmit(object, vals, this);
		}
	}

	@Override
	public void registCommand(Filtercommand<R> command) {
		// TODO Auto-generated method stub
		List<Filtercommand<R>> temps = Lists.newArrayList();
		temps.add( command );
		if( null == this.commands ) {
			this.commands = temps.iterator();
		}else {
			while( this.commands.hasNext() ) {
				temps.add( this.commands.next() );
			}
			temps.sort((c1, c2) -> ( c1.compareTo(c2) ) );
			this.commands = temps.iterator();
		}
	}

	@Override
	public void setTransactionSign(Boolean hasOpen) {
		// TODO Auto-generated method stub
		if( null == hasOpen ) {
			return; // 默认为false
		}
		this.hasGlobalTransAction = hasOpen;
	}

	@Override
	public void setTransactionTemplate(TransactionTemplate template) {
		// TODO Auto-generated method stub
		this.template = template;
	}
	
}
