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
 * zyiyuan 11:08:25 AM 创建
 */
package com.ruoyi.zxydk.flows.services.impls;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.zxydk.domain.factory.ZxydkDomainFactory;
import com.ruoyi.zxydk.eventbus.bus.ZxydkEventBus;
import com.ruoyi.zxydk.flows.annotation.handles.ZxydkFlowChatHandler;
import com.ruoyi.zxydk.flows.annotation.handles.ZxydkFlowNodeHandler;
import com.ruoyi.zxydk.flows.annotation.interfaces.ZxydkFlowChat;
import com.ruoyi.zxydk.flows.annotation.interfaces.ZxydkFlowNode;
import com.ruoyi.zxydk.flows.constants.ZxydkFlowContants;
import com.ruoyi.zxydk.flows.model.arggreroot.chat.ZxydkActionFlow;
import com.ruoyi.zxydk.flows.model.arggreroot.engine.ZxydkFlowEngine;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionNode;
import com.ruoyi.zxydk.flows.model.entity.engine.ZxydkActorFlowEngine;
import com.ruoyi.zxydk.flows.repertory.chat.ZxydkFlowChatElementCreator;
import com.ruoyi.zxydk.flows.services.ZxydkFlowDomainService;

import io.jsonwebtoken.lang.Assert;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class ZxydkFlowDomainServiceImpl<T extends Object> implements ZxydkFlowDomainService<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 930554907593062788L;
	
	
	// 日志
	private static Logger logger = LoggerFactory.getLogger(ZxydkFlowDomainServiceImpl.class);
	
	@Autowired
	private ZxydkFlowChatElementCreator flowBaseActionCreator;
	
	/** 流程引擎 */
	private Map< ZxydkActionFlow.Key , ZxydkFlowEngine > engines = Maps.newHashMap();
	
	
	private AutowireCapableBeanFactory capableBeanFactory;
	
	/** 静态的流程文件 */
	private List<Resource> resources = Lists.newArrayList();

	private ApplicationContext applicationContext;
	
	@Autowired
	private ZxydkDomainFactory domainFactory;
	
	@Autowired
	private ZxydkEventBus eventBus;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
		this.capableBeanFactory = this.applicationContext.getAutowireCapableBeanFactory();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		logger.info("********************************** by annotation 加载流程图开始 ********************************************************************");
		Map<String , ZxydkActionNode> flowActionNodes = this.applicationContext.getBeansOfType(ZxydkActionNode.class, false, true);
		if( null == flowActionNodes || 0 == flowActionNodes.size() ) {
			logger.info("********************************** by annotation 加载流程图 , 系统中未发现有流程，加载结束 ********************************************************************");
			return;
		}
		for( Entry<String, ZxydkActionNode> nodes : flowActionNodes.entrySet() ) {
			ZxydkActionNode node = nodes.getValue();
			if( !node.getClass().isAnnotationPresent(ZxydkFlowChat.class) ) {
				throw new BusinessException( String.format(" load flow by annotation error , node:%s,must has @ZxydkFlowChat!" , node.getClass().getName() ) );
			}
			if( !node.getClass().isAnnotationPresent(ZxydkFlowNode.class) ) {
				throw new BusinessException( String.format("load flow by annotation error , node :%s , must has @ZxydkFlowNode!", node.getClass().getName()) );
			}
			ZxydkFlowChat annotationChat = node.getClass().getAnnotation(ZxydkFlowChat.class);
			ZxydkFlowNode flowNode = node.getClass().getAnnotation(ZxydkFlowNode.class);
			ZxydkFlowChatHandler chatHandle = new ZxydkFlowChatHandler(annotationChat.name(), annotationChat.version(), annotationChat.hasOpenLogger(), annotationChat.hasTransaction() ); 
			ZxydkFlowEngine engine = this.engines.get( chatHandle.actionFlowKey() ); 
			if( null == engine ) {
				/** 新流程 */ 
				engine = new ZxydkActorFlowEngine( );
				this.capableBeanFactory.autowireBeanProperties( engine, 0, false );
				// 流程图总定义节点
				ZxydkActionFlow flow = chatHandle.fillIntoFlowBaseInfo( this.flowBaseActionCreator );
				ZxydkFlowNodeHandler nodeHandle = new ZxydkFlowNodeHandler( flowNode.name(), flow.getHasOpenTransaction()? Boolean.FALSE : flowNode.hasTransaction(), flowNode.conditions() );
				flow.addActionNode( nodeHandle.createActionNode( flow , node , this.flowBaseActionCreator) );
				engine.manualRegistActionFlow( flow );
				manualAddFlowEngine( engine );
			}else {
				/** 老流程 */ 
				Assert.isTrue(engine.getFlowInfo().getHasOpenTransaction().equals(chatHandle.getHasTransaction()) , String.format( " annotation flow create error , node : %s , hastransaction is diffrent now! ", node.getClass().getName()) );
				ZxydkFlowNodeHandler nodeHandle = new ZxydkFlowNodeHandler( flowNode.name(), engine.getFlowInfo().getHasOpenTransaction()? Boolean.FALSE : flowNode.hasTransaction(), flowNode.conditions() );
				engine.getFlowInfo().addActionNode( nodeHandle.createActionNode( engine.getFlowInfo() , node , this.flowBaseActionCreator) );
			}
		}
		this.engines.forEach( (k, engine) -> engine.getFlowInfo().reflushTransaction() );
		logger.info("********************************** by annotation 加载流程图结束 ********************************************************************");
	}

	@Override
	public void start(String flowName, String flowVersion, T target, Map<String, Object> vals) {
		this.execute(flowName, flowVersion, ZxydkFlowContants.FLOW_SIGN_OF_START_ACTOR_NODE, target, vals);
	}

	@Override
	public void execute(String flowName, String flowVersion, String nodeName, T target,
						Map<String, Object> vals) {
		ZxydkFlowEngine engine = obtainFlowEngine( new ZxydkActionFlow.Key(flowName, flowVersion) );
		engine.execute(nodeName, target, vals);
	}
	
	private ZxydkFlowEngine obtainFlowEngine( ZxydkActionFlow.Key key ) {
		ZxydkFlowEngine engine = this.engines.get(key);
		if( null == engine ) {
			throw new RuntimeException("流程引擎搜索失败，未找到需要执行得流程图！");
		}
		engine.confirmFlow(key);
		return engine;
	}

	@Override
	public void manualRegistFlowResource(Resource res) {
		// TODO 等待 可视化的 流程图 的注入。 动态执行的 ？？
	}
	
	public void manualAddFlowEngine( ZxydkFlowEngine flowEngine ) {
		if( null == this.engines ) {
			this.engines = Maps.newHashMap();
		}
		this.engines.put(flowEngine.getUniqueKey(), flowEngine);
	}

}
