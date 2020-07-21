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
 * zyiyuan 10:46:54 AM 创建
 */
package com.ruoyi.zxydk.flows.annotation.handles;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.ruoyi.zxydk.flows.annotation.interfaces.ZxydkFlowTransition;
import com.ruoyi.zxydk.flows.model.arggreroot.chat.ZxydkActionFlow;
import com.ruoyi.zxydk.flows.model.entity.chat.actor.ZxydkActionTransitionLineActor;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionNode;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionTransition;
import com.ruoyi.zxydk.flows.repertory.chat.ZxydkFlowChatElementCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class ZxydkFlowNodeHandler implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5097870036447534890L;
	
	/** 流程节点名 */
	private String name;
	
	/** 流程节点事务标识 */
	private Boolean hasTransaction = Boolean.TRUE;
	
	/** 流程的各个 */
	private List<ZxydkFlowTransitionHandler> conditions = Lists.newArrayList();

	public ZxydkFlowNodeHandler(String name, Boolean hasTransaction, ZxydkFlowTransition[] trans ) {
		super();
		this.name = name;
		this.hasTransaction = hasTransaction;
		fillTransactionHandle(trans);
	}
	
	
	public void fillTransactionHandle( ZxydkFlowTransition[] trans ) {
		
		for( ZxydkFlowTransition tran : trans ) {
			
			ZxydkFlowTransitionHandler transHandle = new ZxydkFlowTransitionHandler( tran.event(), tran.desc() , tran.to() );
			
			this.addFlowTransactionHandle(transHandle);
		}
	}
	
	
	public void addFlowTransactionHandle( ZxydkFlowTransitionHandler transaction ) {
		
		if( null == this.conditions ) {
			
			this.conditions = Lists.newArrayList();
		}
		
		this.conditions.add(transaction);
	}

	public ZxydkActionNode createActionNode(ZxydkActionFlow actionFlow , ZxydkActionNode node, ZxydkFlowChatElementCreator flowCreator) {
		node.setNodeName(this.getName());
		node.setOpenLogger( actionFlow.getHasOpenLogger() );
		node.setFlowName(actionFlow.getUniqueKey().getName());
		node.setFlowVersion(actionFlow.getUniqueKey().getVersion());
		node.setChatTransaction(actionFlow.getHasOpenTransaction());
		for( ZxydkFlowTransitionHandler actionHandle : this.conditions ) {
			ZxydkActionTransition transition = flowCreator.createInstance(ZxydkActionTransitionLineActor.class); 
			transition.setEvent(actionHandle.getEvent());
			transition.setDesc(actionHandle.getDesc());
			transition.setTo(actionHandle.getTo());
			transition.setFrom( node.getNodeName() );
			transition.setFromNode(node);
			transition.intoFlowName(node.getFlowName() );
			transition.intoFlowVersion( node.getFlowVersion() ); 
			node.registTransition(transition);
		}
		return node;
	}
	
}
