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
 * zyiyuan 9:30:44 AM 创建
 */
package com.ruoyi.zxydk.flows.model.entity.chat.element;

import com.ruoyi.zxydk.flows.model.arggreroot.chat.base.ZxydkFlowBaseAction;


/**
 *  @category 流程图元素  -  线 （节点关系接口）
 */
@SuppressWarnings("all")
public interface ZxydkActionTransition<T extends Object> extends ZxydkFlowBaseAction<T> {
	
	/******************************- 获得 - 线 - 的 - 描述 -***************************************************************************************/
	/**
	 * @category 设置 线 的描述信息 
	 * 
	 * @param desc [String]
	 * 
	 */
	public void setDesc( String desc );
	
	/**
	 * @category 获得 线的描述信息 
	 */
	public String getDesc();
	
	/******************************- 获得 - 线 - 的 -  -驱动事件名***************************************************************************************/
	/**
	 * @category 设置 线的唯一标识名
	 * 
	 * @param eventName
	 * 
	 */
	public void setEvent( String eventName );
	
	/**
	 * @category  获得 线的唯一标识名
	 */
	public String getEvent();
	
	/******************************- 获得 - 线 - 终点（节点名） -***************************************************************************************/
	/**
	 * @category 设置 线的 To的标识值 
	 * 
	 * @param toName
 	 *  
	 */
	public void setTo( String toName );
	
	
	/**
	 * @category 获得 线的To的标识值 
	 */
	public String getTo( );
	
	/******************************- 获得 - 线 - 起点（节点名） -***************************************************************************************/
	/**
	 * @category 设置 线的 from的标识值 
	 * 
	 * @param fromName
 	 *  
	 */
	public void setFrom( String fromName );
	
	
	/**
	 * @category 获得 线的From的标识值 
	 */
	public String getFrom( );
	
	/******************************- 获得 - 线 - 终点（节点） -***************************************************************************************/
	/**
	 * @category 设置 To的节点信息
	 * 
	 * @param toNode  [ ZxydkActionNode ]
	 * 
	 */
	public void setToNode( ZxydkActionNode toNode );

	/**
	 *  @category 获得 To的节点信息
	 *  
	 */
	public ZxydkActionNode getToNode();
	
	
	/******************************- 获得 - 线 - 起点（节点） -***************************************************************************************/
	/**
	 * @category 设置 from的节点信息
	 * 
	 * @param fromNode  [ ZxydkActionNode ]
	 * 
	 */
	public void setFromNode( ZxydkActionNode fromNode );

	/**
	 *  @category 获得 from的节点信息
	 *  
	 */
	public ZxydkActionNode getFromNode();
	
	
	/******************************- 获得 - 线 - 所在流程图（矢量图）的版本号 -***************************************************************************************/
	/**
	 * @category  流程版本号 
	 * 
	 * @param flowVersion
	 * 
	 */
	public void intoFlowVersion( String flowVersion );
	
	/**
	 *  @category 获得流程版本
	 * 
	 *  @return flowName
	 *  
	 */
	public String flowVersion();
	
	
	/******************************- 获得 - 线 - 所在流程图（矢量图）的名称 -***************************************************************************************/
	/**
	 *  @category 流程名称
	 * 
	 *  @param flowName
	 *  
	 */
	public void intoFlowName( String flowName );
	
	/**
	 *  @category 获得流程名称
	 * 
	 *  @return flowName
	 *  
	 */
	public String flowName();
	
}
