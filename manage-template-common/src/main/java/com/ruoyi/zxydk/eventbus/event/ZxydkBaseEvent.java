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
 * zyiyuan 4:47:20 PM 创建
 */
package com.ruoyi.zxydk.eventbus.event;

import com.ruoyi.zxydk.domain.entity.ZxydkAbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @category 事件分发的事件载体 总定义 
 * 
 */
@Getter
@Setter
@ToString
public abstract class ZxydkBaseEvent extends ZxydkAbstractEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1403689645756958387L;
	
	
	
	
}
