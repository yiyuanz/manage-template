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
 * zyiyuan 10:42:00 AM 创建
 */
package com.ruoyi.zxydk.flows.model.entity.chat.actor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Lists;
import com.ruoyi.zxydk.common.transaction.ZxydkTransactionTemplate;
import com.ruoyi.zxydk.domain.factory.ZxydkDomainFactory;
import com.ruoyi.zxydk.eventbus.bus.ZxydkEventBus;
import com.ruoyi.zxydk.flows.constants.ZxydkFlowContants;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionConditionSelector;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionNode;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionTransition;
import com.ruoyi.zxydk.flows.repertory.chat.ZxydkFlowChatElementCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class ZxydkActionAbstractNode<T extends Object> implements ZxydkActionNode<T> {

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8987145746093696353L;
	
	// 日志
	protected static final Logger logger = LoggerFactory.getLogger(ZxydkActionAbstractNode.class);
	
	/** 流程节点 */
	private String name;
	
	/** 流程节点所属 流程图名称（唯一标识） */
	private String flowName;
	
	/** 流程节点所属 流程图版本号 */
	private String flowVersion;
	
	/** 是否开启事务 */
	private Boolean hasTransaction = Boolean.FALSE;
	
	/** 是否打开日志跟踪器 默认为不打开，是由配置文件决定 */
	private Boolean hasOpenLogger = Boolean.FALSE;
	
	/** 全局流程的事务开关  */
	private Boolean hasChatTransaction = Boolean.FALSE;
	
	/** 条件选择器 */
	private ZxydkActionConditionSelector condition = new ZxydkActionConditionSelectorActor();
	
	@Autowired
	protected ZxydkFlowChatElementCreator flowCreator;
	
	/** 事件总线 */
	@Autowired
	protected ZxydkEventBus eventBus;
	
	/** 领域驱动工厂 */
	@Autowired
	protected ZxydkDomainFactory domainFactory;
	
	/** 事务模板 */
	@Autowired
	protected TransactionTemplate template;

	
	@Override
	public void setNodeName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public String getNodeName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setTransaction(boolean isTransaction) {
		// TODO Auto-generated method stub
		this.hasTransaction = isTransaction;
	}

	@Override
	public boolean isTransaction() {
		// TODO Auto-generated method stub
		return this.hasTransaction;
	}

	@Override
	public void setOpenLogger(Boolean hasOpenLogger) {
		// TODO Auto-generated method stub
		this.hasOpenLogger = hasOpenLogger;
	}

	@Override
	public void setChatTransaction(Boolean hasOpen) {
		// TODO Auto-generated method stub
		this.hasChatTransaction = hasOpen;
	}

	@Override
	public void setFlowVersion(String flowVersion) {
		// TODO Auto-generated method stub
		this.flowVersion = flowVersion;
	}

	@Override
	public void setFlowName(String flowName) {
		// TODO Auto-generated method stub
		this.flowName = flowName;
	}

	@Override
	public String getFlowVersion() {
		// TODO Auto-generated method stub
		return this.flowVersion;
	}

	@Override
	public String getFlowName() {
		// TODO Auto-generated method stub
		return this.flowName;
	}

	@Override
	public void reflushTransition(Map<String, ZxydkActionNode<T>> actionNodes) {
		// TODO Auto-generated method stub
		if( !Lists.newArrayList( ZxydkFlowContants.FLOW_SIGN_OF_END_ACTOR_NODE, ZxydkFlowContants.FLOW_SIGN_OF_START_ACTOR_NODE ).contains(this.name) ) {
			if( null == this.condition || null == this.condition.getTransactionLines() || 0 == this.condition.getTransactionLines().size() ) {
				logger.error("为节点：{}刷新线路关系时，发现未配置条件选择器！流程：{}，版本：{}！", this.name, this.flowName, this.flowVersion);
				throw new RuntimeException(String.format("为节点：{%s}刷新线路关系时，发现未配置条件选择器！流程：{%s}，版本：{%s}！", this.name, this.flowName, this.flowVersion ));
			}
		}
		Iterator<Entry<String, ZxydkActionTransition>> ite = this.condition.getTransactionLines().entrySet().iterator();
		while( ite.hasNext() ) {
			ZxydkActionTransition<T> transaction = ite.next().getValue();
			ZxydkActionNode<T> node = actionNodes.get(transaction.getTo());
			if( null == node || StringUtils.isBlank(node.getNodeName()) ) {
				logger.error("为节点：{}刷新线路关系时, To节点无法找到，toName is {}！", this.name, null == node ? null : node.getNodeName() );
				throw new RuntimeException(String.format( "为节点：{}刷新线路关系时, To节点无法找到，toName is {}！",this.name, null == node ? null : node.getNodeName()) );
			}
			transaction.setToNode(node);
			transaction.setFromNode(this);
		}
	}

	@Override
	public void registTransition(ZxydkActionTransition<T> transaction) {
		// TODO Auto-generated method stub
		if( null == this.condition ) {
			this.condition = flowCreator.createInstance(ZxydkActionConditionSelector.class);
		}
		this.condition.addTransaction(transaction);
	}
	
	@Override
	public void action(T target, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		/** 打印前置日志 */ 
		loggerBeforeTrack( target, vals, now );
		if( this.hasTransaction && !this.hasChatTransaction ) {
			// 事务节点
			new ZxydkTransactionTemplate<Void>(template) {
				@Override
				protected Void justDoIt() {
					// 事前处理
					before( target, vals );
					// 核心处理
					getCondition().setResultEvent( execute( target, vals ) );
					// 事后处理
					after( target, vals );
					return null;
				}
			}.template();
		}else {
			// 无事务节点
			// 事前处理
			before( target, vals );
			// 核心处理
			getCondition().setResultEvent( execute( target, vals ) );
			// 事后处理
			after( target, vals );
		}
		/** 打印后置置日志 */ 
		loggerAfterTrack( target, vals , now );
		/** 流程推进 */
		pushForward( target, vals );
	}
	
	@Override
	public String execute(T target, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void before(T target, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after(T target, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @category  流程推进
	 * 
	 * @param target T  流程推进传递信息
	 * 
	 * @param vals (Map<String,Object> ) 流程推进传递的扩展信息
 	 * 
	 */
	private void pushForward(T target, Map<String, Object> vals) {

		if( ZxydkFlowContants.FLOW_SIGN_OF_END_ACTOR_NODE.equals(this.name) ) {
			// 是否到了最后节点 flow_end
			return;
		}
		// 推进节点
		this.getCondition().action(target, vals);
	}
	
	/**
	 * @category 日志跟踪打印  before track
	 * 
	 * @param target T  流程推进传递信息
	 * 
	 * @param vals (Map<String,Object> ) 流程推进传递的扩展信息
	 * 
	 * @param now long 
	 * 
	 */
	private  void loggerBeforeTrack(T target, Map<String, Object> vals, long now) {
		
		if( this.hasOpenLogger ) {
			
			logger.info("--- 流程【{}】--- 版本【{}】 --- 节点【{}】 --- 执行开始！", this.flowName, this.flowVersion , this.name );
		}
	}
	
	/**
	 * @category 日志跟踪打印  after track
	 */
	private void loggerAfterTrack( T target , Map<String, Object> vals , long now ){
		 
		if( this.hasOpenLogger ) {
			
			logger.info("--- 流程【{}】--- 版本【{}】 --- 节点【{}】 --- 执行结束， 节点耗时：【{}】ms！", this.flowName, this.flowVersion , this.name, System.currentTimeMillis() - now);
		}
	}
	
}
