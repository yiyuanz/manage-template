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
 * zyiyuan 2:19:33 PM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.test;

import com.ruoyi.zxydk.threadpool.forkjoin.interfaces.ZxydkJoin;
import com.ruoyi.zxydk.threadpool.forkjoin.invokebase.ZxydkBaseJoin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyJoinTest extends ZxydkBaseJoin {

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4059117098660750408L;
	
	private int sum;

	@Override
	public void initParam() {
		this.sum = 0;
	}

	@Override
	public <V extends ZxydkJoin> V merge(V join) {
		this.checkMegeClassType(join);
		MyJoinTest otherJoin = (MyJoinTest)join;
		this.sum += otherJoin.getSum();
		return (V) this;
	}
	
}
