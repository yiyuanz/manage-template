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
 * zyiyuan 11:23:10 AM 创建
 */
package com.ruoyi.zxydk.flows.model.objectvalue;

import java.util.Map;

import com.ruoyi.zxydk.flows.model.entity.chat.actor.ZxydkActionAbstractNode;

public class ZxydkActionEndNode<T extends Object> extends ZxydkActionAbstractNode<T> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 435755195451035469L;

	@Override
	public String execute(T target, Map<String, Object> vals) {
		
		logger.info(" --- 流程：【{}】 --- 版本：【{}】 --- 节点【{}】 --- 流程已处理到最后节点，并结束流程！", this.getFlowName(), this.getFlowVersion(), this.getNodeName());
		
		return null;
	}
}
