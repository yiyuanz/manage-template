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
 * zyiyuan 9:50:42 AM 创建
 */
package com.ruoyi.zxydk.flows.model.entity.chat.element;

import java.util.Map;

import com.ruoyi.zxydk.flows.model.arggreroot.chat.base.ZxydkFlowBaseAction;


/**
 * @category 流程图元素 - 节点条件选择器 
 * 
 */
@SuppressWarnings("all")
public interface ZxydkActionConditionSelector<T extends Object> extends ZxydkFlowBaseAction<T> {
	
	/****************************- 条件选择器 - 添加选择可执行的线条 -**************************************************************************/
	/**
	 * @category 添加预判的结果关系 
	 * 
	 * @param transaction
	 * 
	 */
	public void addTransaction( ZxydkActionTransition transaction );
	
	/**
	 * @category  获得 该选择器下的 所有路由的线
	 */
	public Map<String, ZxydkActionTransition> getTransactionLines();
	
	/**
	 * @category  设置 该选择器下的 所有路由的线
	 */
	public void setTransactionLines( Map<String, ZxydkActionTransition> maps );
	
	
	/****************************- 条件选择器 - 挂载节点 -**************************************************************************/
	/**
	 * @category 获得 选择器所挂的节点 
	 * 
	 */
	public ZxydkActionNode getActionNode();
	
	
	/**
	 * @category 设置 选择器所挂的节点 
	 */
	public void setActionNode( ZxydkActionNode node );
	
	/****************************- 条件选择器 - 业务执行后结果导向 - fact目标 -**************************************************************************/
	/**
	 * @category 设置 节点执行后，选择器的路由结果
	 */
	public void setResultEvent( String event );
	
	
	/**
	 * @category  设置 获得选择器的路由结果
	 */
	public String getResultEvent();
	
}
