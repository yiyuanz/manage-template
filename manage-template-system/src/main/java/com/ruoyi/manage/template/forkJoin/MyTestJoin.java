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
 * zyiyuan 3:42:59 PM 创建
 */
package com.ruoyi.manage.template.forkJoin;

import com.ruoyi.zxydk.threadpool.forkjoin.interfaces.ZxydkJoin;
import com.ruoyi.zxydk.threadpool.forkjoin.invokebase.ZxydkBaseJoin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyTestJoin extends ZxydkBaseJoin {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3639943865300318206L;

	private String resc ;
	
	@Override
	public <V extends ZxydkJoin> V merge(V join) {
		this.checkMegeClassType(join);
		MyTestJoin otherJoin = (MyTestJoin) join;
		this.resc += otherJoin.getResc();
		return (V) this;
	}

	@Override
	public void initParam() {
		// TODO Auto-generated method stub
		resc= "";
	}
	
}
