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
 * zyiyuan 10:21:19 AM 创建
 */
package com.ruoyi.zxydk.flows.annotation.handles;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.ruoyi.zxydk.flows.constants.ZxydkFlowContants;
import com.ruoyi.zxydk.flows.model.arggreroot.chat.ZxydkActionFlow;
import com.ruoyi.zxydk.flows.model.arggreroot.chat.actor.ZxydkActionFlowActor;
import com.ruoyi.zxydk.flows.model.objectvalue.ZxydkActionEndNode;
import com.ruoyi.zxydk.flows.repertory.chat.ZxydkFlowChatElementCreator;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class ZxydkFlowChatHandler implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1750806894359019795L;
	
	/** 流程图名 */
	private String name;
	
	/** 流程图版本 */
	private String version;
	
	/** 流程图中是否打开流程日志跟踪 */
	private Boolean hasOpenLogger = Boolean.FALSE;
	
	/** 流程图中是否开启事务 */
	private Boolean hasTransaction = Boolean.FALSE;
	
	/** 流程图中节点信息 */
	private List<ZxydkFlowNodeHandler> nodeHandles = Lists.newArrayList();

	// 
	public ZxydkFlowChatHandler(	String name, String version, Boolean hasOpenLogger,  Boolean hasTransaction) {
		super();
		this.name = name;
		this.version = version;
		this.hasOpenLogger = hasOpenLogger;
		this.hasTransaction = hasTransaction;
	}

	public ZxydkFlowChatHandler(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}
	
	public void addFlowNode( ZxydkFlowNodeHandler flowNode ) {
		if( null == this.nodeHandles ) {
			this.nodeHandles = Lists.newArrayList();
		}
		this.nodeHandles.add(flowNode);
	}
	
	public ZxydkActionFlow fillIntoFlowBaseInfo( ZxydkFlowChatElementCreator creator ) {
		ZxydkActionFlow flow = creator.createInstance(ZxydkActionFlowActor.class);
		flow.setUniqueKey(new ZxydkActionFlow.Key( this.getName() , this.getVersion()) ); 
		flow.setHasOpenLogger( this.getHasOpenLogger() ); 
		flow.setHasOpenTransaction( this.getHasTransaction() );
		//  init end node
		ZxydkActionEndNode endNode = creator.createInstance(ZxydkActionEndNode.class);
		endNode.setNodeName(ZxydkFlowContants.FLOW_SIGN_OF_END_ACTOR_NODE);
		endNode.setFlowVersion(this.getVersion());
		endNode.setHasOpenLogger(hasOpenLogger);
		endNode.setTransaction(Boolean.FALSE);
		endNode.setChatTransaction(flow.getHasOpenTransaction());
		endNode.setFlowName(this.getName());
		flow.addActionNode(endNode);
		return flow;
	}
	
	
	
	public ZxydkActionFlow.Key actionFlowKey(){
		
		return new ZxydkActionFlow.Key( this.name, this.version );
	}
}
