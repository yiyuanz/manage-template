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
 * zyiyuan 9:55:33 AM 创建
 */
package com.ruoyi.zxydk.flows.model.arggreroot.chat.actor;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Maps;
import com.ruoyi.zxydk.common.transaction.ZxydkTransactionTemplate;
import com.ruoyi.zxydk.flows.constants.ZxydkFlowContants;
import com.ruoyi.zxydk.flows.model.arggreroot.chat.ZxydkActionFlow;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionNode;

import lombok.Getter;
import lombok.Setter;


/**
 * @category 流程矢量图 - 矢量图 
 * 
 */
@Getter
@Setter
@SuppressWarnings("all")
public class ZxydkActionFlowActor<T extends Object> implements ZxydkActionFlow<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2038731633048348874L;
	
	// 日志
	private static final Logger logger = LoggerFactory.getLogger(ZxydkActionFlowActor.class);
	
	/**
	 * @category 流程矢量图的坐标
	 */
	private ZxydkActionFlow.Key  key;
	
	/** spring 默认事务模板 */
	@Autowired
	private TransactionTemplate template;
	
	/** 跟踪日志开关 默认：关闭   */
	private Boolean hasOpenLogger = Boolean.FALSE;
	
	
	/** 流程图全局事务开关 默认：关闭 */
	private Boolean hasOpeanTransaction = Boolean.FALSE;
	
	/** 流程图中所有节点信息  */
	private Map<String, ZxydkActionNode<T>> nodes = Maps.newHashMap() ;
	
	/** @category spring 处理上下文  */
	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.context = applicationContext;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reflushTransaction() {
		// TODO Auto-generated method stub
		for( Entry< String, ZxydkActionNode<T>> entry : this.nodes.entrySet() ) {
			ZxydkActionNode<T> node = entry.getValue();
			if( ZxydkFlowContants.FLOW_SIGN_OF_END_ACTOR_NODE.equals( node.getNodeName()) ) {
				continue;
			}
			node.reflushTransition(nodes);
		}
	}
	
	@Override
	public void action(T target, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		this.actionFromNode(ZxydkFlowContants.FLOW_SIGN_OF_START_ACTOR_NODE, target, vals);
	}
	
	@Override
	public void actionFromNode(String nodeName, T target, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		ZxydkActionNode node = this.nodes.get(nodeName);
		if( null == node || StringUtils.isBlank(nodeName) ) {
			logger.error(" 流程引擎从节点执行时，发生空异常，nodeName：{} 非该：{}流程图中的节点信息！", nodeName , node );
			throw new RuntimeException( String.format( "流程引擎从节点执行时，发生空异常,nodeName:%s 非流程图节点！", nodeName ) ) ;
		}
		if( null != this.hasOpeanTransaction && this.hasOpeanTransaction ) {
			new ZxydkTransactionTemplate<Void>(template) {
				@Override
				protected Void justDoIt() {
					// TODO Auto-generated method stub
					node.action(target, vals);
					return null;
				}
			}.template();
		}else {
			node.action(target, vals);
		}
	}

	@Override
	public void addActionNode(ZxydkActionNode<T> actionNode) {
		// TODO Auto-generated method stub
		if( null == nodes ) {
			nodes = Maps.newHashMap();
		}
		actionNode.setOpenLogger( this.hasOpenLogger );
		actionNode.setChatTransaction(this.hasOpeanTransaction); 
		nodes.put( actionNode.getNodeName(), actionNode );
	}

	@Override
	public void setUniqueKey(Key key) {
		// TODO Auto-generated method stub
		this.key = key;
	}

	@Override
	public Key getUniqueKey() {
		// TODO Auto-generated method stub
		return this.key;
	}

	@Override
	public void setHasOpenTransaction(Boolean isOpen) {
		// TODO Auto-generated method stub
		this.hasOpeanTransaction = isOpen;
	}

	@Override
	public Boolean getHasOpenTransaction() {
		// TODO Auto-generated method stub
		return this.hasOpeanTransaction;
	}

	@Override
	public void setHasOpenLogger(Boolean isOpen) {
		// TODO Auto-generated method stub
		this.hasOpenLogger = isOpen;
	}

	@Override
	public Boolean getHasOpenLogger() {
		// TODO Auto-generated method stub
		return this.hasOpenLogger;
	}
	
}
