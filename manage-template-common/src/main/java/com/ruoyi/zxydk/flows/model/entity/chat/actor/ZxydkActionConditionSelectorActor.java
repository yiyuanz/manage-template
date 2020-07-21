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
 * zyiyuan 10:36:58 AM 创建
 */
package com.ruoyi.zxydk.flows.model.entity.chat.actor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionConditionSelector;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionNode;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionTransition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class ZxydkActionConditionSelectorActor<T extends Object> implements ZxydkActionConditionSelector<T> {

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8379019526620581985L;
	
	// 日志
	private static Logger logger = LoggerFactory.getLogger(ZxydkActionConditionSelectorActor.class);
	
	/** 选择器 所挂载节点 */
	private ZxydkActionNode<T> node;
	
	/** 选择器 所承载的流程图的路由线路 */
	private Map<String, ZxydkActionTransition> transactionLines = Maps.newHashMap();
	
	/** 选择器执行的结果 */
	private String resultEvent;
	
	
	@Override
	public void action(T target, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		ZxydkActionTransition<T> transaction = this.transactionLines.get(this.resultEvent);
		if( null == this.resultEvent ) {
			logger.error("流程推进时，选择器的执行结果为空！name:{}", this.getClass().getName());
			throw new RuntimeException("流程推进时，选择器的执行结果为空！");
		}
		if( null == transaction ) {
			logger.error("流程推进时，选择器的执行结果无法匹配到具体的线路！name:{}", this.getClass().getName() );
			throw new RuntimeException("流程推进时，选择器的执行结果无法匹配到具体的线路！");
		}
		transaction.action(target, vals);
	}

	@Override
	public void addTransaction(ZxydkActionTransition transaction) {
		// TODO Auto-generated method stub
		if( null == this.transactionLines ) {
			this.transactionLines = Maps.newHashMap();
		}
		this.transactionLines.put(transaction.getEvent(), transaction);
	}

	@Override
	public Map<String, ZxydkActionTransition> getTransactionLines() {
		// TODO Auto-generated method stub
		return this.transactionLines;
	}

	@Override
	public void setTransactionLines(Map<String, ZxydkActionTransition> maps) {
		// TODO Auto-generated method stub
		this.transactionLines = maps;
	}

	@Override
	public ZxydkActionNode getActionNode() {
		// TODO Auto-generated method stub
		return this.node;
	}

	@Override
	public void setActionNode(ZxydkActionNode node) {
		// TODO Auto-generated method stub
		this.node = node;
	}

	@Override
	public void setResultEvent(String event) {
		// TODO Auto-generated method stub
		this.resultEvent = event;
	}

	@Override
	public String getResultEvent() {
		// TODO Auto-generated method stub
		return this.resultEvent;
	}
	
}
