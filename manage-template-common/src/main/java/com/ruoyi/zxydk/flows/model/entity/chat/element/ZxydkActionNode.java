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
 * zyiyuan 9:27:51 AM 创建
 */
package com.ruoyi.zxydk.flows.model.entity.chat.element;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;

import com.ruoyi.zxydk.flows.model.arggreroot.chat.base.ZxydkFlowBaseAction;

/**
 * @category 流程图元素 -  节点
 *  
 */
public interface ZxydkActionNode<T extends Object> extends ZxydkFlowBaseAction<T> , ApplicationContextAware , InitializingBean {
	
	
	/**************************************- 流程节点 - 节点名定义（唯一标识） -********************************************************************************/
	/** 设置 node的名字属性 name  */
	public void setNodeName(String name);
	
	/** 获得 node的名字属性 name */
	public String getNodeName();
	
	/**************************************- 流程节点 - 事务开关（如果有全局事务 该事务无效） -*******************************************************************/
	/** 设置 node的事务标识  */
	public void setTransaction(boolean isTransaction);
	
	/** 获得事务标识 */
	public boolean isTransaction();
	
	/**************************************- 流程节点 - 跟踪日志开关 -*******************************************************************/
	/** 是否开启流程跟踪日志 */
	public void setOpenLogger( Boolean hasOpenLogger );
	
	/**************************************- 流程节点 - 全局事务 -*******************************************************************/
	/** 设置全局事务开关 */
	public void setChatTransaction( Boolean hasOpen );
	
	/**************************************- 流程节点 - 归属流程图的坐标 （name， version） -*******************************************************************/
	/** 设置flow的版本 */
	public void setFlowVersion(String flowVersion);
	
	/** 设置flow的空间命名 */
	public void setFlowName(String flowName);
	
	/** 获得 flow的版本 */
	public String getFlowVersion();
	
	/** 获得flow的空间命名 */
	public String getFlowName();
	
	/**************************************- 流程节点 - 核心业务方法 -*******************************************************************/
	/**
	 * @category 核心执行方法 
	 * 
	 * @param T target 流程节点传递信息
	 * 
	 * @param vals Map<String, Object> 流程节点传递的扩展信息
	 * 
	 */
	public String execute( T target , Map<String, Object>  vals );
	
	/**************************************- 流程节点 - 核心业务前置方法 -*******************************************************************/
	/**
	 * @category 前置处理方法
	 * 
	 * @param T target 流程节点传递信息
	 * 
	 * @param vals Map<String, Object> 流程节点传递的扩展信息
	 * 
	 */
	public void before(T target, Map<String, Object> vars) ;

	/**************************************- 流程节点 - 核心业务后置方法 -*******************************************************************/
	/**
	 * @category 后置护理方法 
	 * 
	 * @param T target 流程节点传递信息
	 * 
	 * @param vals Map<String, Object> 流程节点传递的扩展信息
	 */
	public void after(T target, Map<String, Object> vars) ;
	
	
	/**************************************- 流程节点 - 图节点 转化为 矢量图节点 -*******************************************************************/
	/**
	 * @category  刷新关系
	 * 
	 * @param actionsNodes  Map<String, ActionNode<T>>
	 *  
	 */
	public void reflushTransition( Map< String, ZxydkActionNode<T>>  actionNodes );
	
	
	/**************************************- 流程节点 - 添加节点的关系线条 -*******************************************************************/
	/**
	 * @category  节点注册 节点的相关 线条
	 * 
	 * @param transaction  ActionTransaction<T>
	 *  
	 */
	public void registTransition( ZxydkActionTransition<T> transaction );
	
}
