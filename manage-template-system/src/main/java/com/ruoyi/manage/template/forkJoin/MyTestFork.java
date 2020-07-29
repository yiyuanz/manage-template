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
 * zyiyuan 3:42:43 PM 创建
 */
package com.ruoyi.manage.template.forkJoin;

import com.ruoyi.zxydk.threadpool.forkjoin.annotation.ZxydkFork;
import com.ruoyi.zxydk.threadpool.forkjoin.invokebase.ZxydkBaseFork;

@ZxydkFork(splitHandle = 4)
public class MyTestFork extends ZxydkBaseFork<String, MyTestJoin> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1027996361215547270L;

	@Override
	protected void eachExecute(String tc) {
		logger.info("线程：{} ， 执行内容：{}" , Thread.currentThread().getName() , tc);
		this.getRawResult().setResc( this.getRawResult().getResc() + ":" + tc );
	}
	
}
