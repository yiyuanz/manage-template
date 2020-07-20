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
 * zyiyuan 4:40:45 PM 创建
 */
package com.ruoyi.system.eventhandle;

import com.ruoyi.zxydk.eventbus.annotation.ZxydkEventHandler;
import com.ruoyi.zxydk.eventbus.eventHandle.ZxydkBaseEventHandle;

import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;



@ZxydkEventHandler
public class TestEventHandle extends ZxydkBaseEventHandle{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 31161288944236289L;

	
	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	public void testHandle( TestEvent test ) {
		System.out.println(test.getTest() + "----0000-----" +test);
	}
}


