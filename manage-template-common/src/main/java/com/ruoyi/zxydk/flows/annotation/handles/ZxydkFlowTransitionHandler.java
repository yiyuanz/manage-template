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
 * zyiyuan 11:16:26 AM 创建
 */
package com.ruoyi.zxydk.flows.annotation.handles;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZxydkFlowTransitionHandler implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4741397230489677455L;
	
	/** 流程执行结果分路 */
	private String event;
	
	/** 流程执行结果分路描述 */
	private String desc;
	
	/** 流程执行下一个节点名 */
	private String to;

	public ZxydkFlowTransitionHandler(String event, String desc, String to) {
		super();
		this.event = event;
		this.desc = desc;
		this.to = to;
	}

	public ZxydkFlowTransitionHandler(String event, String to) {
		super();
		this.event = event;
		this.to = to;
		this.desc = "";
	}
	
}
