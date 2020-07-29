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
 * zyiyuan 9:36:58 AM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.interfaces;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;


@SuppressWarnings("all")
public interface ZxydkJoin extends ApplicationContextAware, InitializingBean , Serializable {
	
	
	
	/**
	 * join 合并 
	 * 
	 */
	public <V extends ZxydkJoin> V merge( V join );
	
	
	/**
	 * 初始化参数 
	 */
	public void initParam();
	
}
