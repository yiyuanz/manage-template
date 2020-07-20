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
 * zyiyuan 2:15:29 PM 创建
 */
package com.ruoyi.zxydk.filterchain.invoke;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import com.ruoyi.zxydk.domain.factory.ZxydkDomainFactory;
import com.ruoyi.zxydk.filterchain.interfaces.FilterCommandChain;
import com.ruoyi.zxydk.filterchain.interfaces.Filtercommand;

public abstract class FilterCommandInvoke<R extends Object> implements Filtercommand<R> {
	
	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 8801812560044504414L;
	
	// 日志
	protected static final Logger logger = LoggerFactory.getLogger(FilterCommandInvoke.class);
	
	// DDD domainFactory
	@Autowired
	protected ZxydkDomainFactory domainFactory;
	
	// transaction
	private Boolean hasOpenTransaction = Boolean.FALSE;
	
	// order by grade of command execute 
	private int orderGrade;
	
	@Override
	public void transmit(R object, Map<String, Object> vals, FilterCommandChain<R> chain) {
		chain.process(object, vals);
	}

	@Override
	public int compareTo(Ordered o) {
		return this.getOrder() - o.getOrder();
	}

	@Override
	public int getOrder() {
		return this.orderGrade;
	}

	@Override
	public Boolean match(R object, Map<String, Object> vals) {
		return Boolean.TRUE; // default to do execute 
	}

	@Override
	public Boolean hasOpenTrAnsaction() {
		return this.hasOpenTransaction;
	}

	@Override
	public void setOpenTransAction(Boolean hasOpen) {
		this.hasOpenTransaction = hasOpen;
	}

	@Override
	public void appoitOrder(int grade) {
		this.orderGrade = grade;
	}
	
}
