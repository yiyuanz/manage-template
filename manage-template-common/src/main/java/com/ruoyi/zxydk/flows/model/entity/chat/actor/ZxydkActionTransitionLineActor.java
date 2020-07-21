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
 * zyiyuan 10:09:40 AM 创建
 */
package com.ruoyi.zxydk.flows.model.entity.chat.actor;

import java.util.Map;

import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionNode;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionTransition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class ZxydkActionTransitionLineActor<T extends Object> implements ZxydkActionTransition<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8981175617815984753L;

	/**  所在的流程图的版本号  */
	private String flowVersion;
	
	/** 所在流程图的名称 */
	private String flowName;
	
	/** 流程图中 对 线的描述 */
	private String desc;
	
	/** 流程图中 对 线的矢量指向定义 */
	private String event;
	
	/** 流程图中 对 线的from头的描述信息  */
	private String from;
	
	/** 流程图中 对 线的to头的描述信息  */
	private String to;
	
	/** 流程图中 对 线的to的节点指向 */
	private ZxydkActionNode<T> toNode;
	
	
	/** 流程图中 对 线的to的节点指向 */
	private ZxydkActionNode<T> fromNode;
	
	
	@Override
	public void action(T target, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		this.getToNode().action(target, vals);
	}

	@Override
	public void setDesc(String desc) {
		// TODO Auto-generated method stub
		this.desc = desc;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return this.desc;
	}

	@Override
	public void setEvent(String eventName) {
		// TODO Auto-generated method stub
		this.event = eventName;
	}

	@Override
	public String getEvent() {
		// TODO Auto-generated method stub
		return this.event;
	}

	@Override
	public void setTo(String toName) {
		// TODO Auto-generated method stub
		this.to = toName;
	}

	@Override
	public String getTo() {
		// TODO Auto-generated method stub
		return this.to;
	}

	@Override
	public void setFrom(String fromName) {
		// TODO Auto-generated method stub
		this.from = fromName;
	}

	@Override
	public String getFrom() {
		// TODO Auto-generated method stub
		return this.from;
	}

	@Override
	public void setToNode(ZxydkActionNode toNode) {
		// TODO Auto-generated method stub
		this.toNode = toNode;
	}

	@Override
	public ZxydkActionNode getToNode() {
		// TODO Auto-generated method stub
		return this.toNode;
	}

	@Override
	public void setFromNode(ZxydkActionNode fromNode) {
		// TODO Auto-generated method stub
		this.fromNode = fromNode;
	}

	@Override
	public ZxydkActionNode getFromNode() {
		// TODO Auto-generated method stub
		return this.fromNode;
	}

	@Override
	public void intoFlowVersion(String flowVersion) {
		// TODO Auto-generated method stub
		this.flowVersion = flowVersion;
	}

	@Override
	public String flowVersion() {
		// TODO Auto-generated method stub
		return this.flowVersion;
	}

	@Override
	public void intoFlowName(String flowName) {
		// TODO Auto-generated method stub
		this.flowName = flowName;
	}

	@Override
	public String flowName() {
		// TODO Auto-generated method stub
		return this.flowName;
	}
	
}
