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
 * zyiyuan 2:16:16 PM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.invokebase;

import org.apache.shiro.util.Assert;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.ruoyi.zxydk.threadpool.forkjoin.interfaces.ZxydkJoin;

public abstract class ZxydkBaseJoin implements ZxydkJoin {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1815507251692574647L;
	
	protected ApplicationContext context;
	
	public ZxydkBaseJoin() {
		super();
		this.initParam();
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	protected <V extends ZxydkJoin> void checkMegeClassType( V join ) {
		Assert.isTrue(this.getClass().getName().equals(join.getClass().getName()) , "执行任务分裂后，合并结果集时，发生类型不匹配！");
	}
}
