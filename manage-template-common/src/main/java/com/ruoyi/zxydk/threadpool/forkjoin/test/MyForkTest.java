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
 * zyiyuan 2:15:33 PM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.test;

import java.util.List;

import com.google.common.collect.Lists;
import com.ruoyi.zxydk.threadpool.forkjoin.invokebase.ZxydkBaseFork;

public class MyForkTest extends ZxydkBaseFork<Integer , MyJoinTest>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5710075295193450302L;

	@Override
	protected void eachExecute(Integer tc) {
		// TODO Auto-generated method stub
		logger.info("线程：{} ， 执行内容：{}" , Thread.currentThread().getName() , tc);
		this.getRawResult().setSum( this.getRawResult().getSum() + tc );
	}
	
	public MyForkTest() {
		super();
	}

	public MyForkTest(int splitPoint, List<Integer> targetCons) {
		super(splitPoint, targetCons);
	}

	public static void main( String[] args ) {
		MyForkTest ft = new MyForkTest( 5 , Lists.newArrayList(10,11,12,13,14,15,16,17,18,19,20) );
		MyJoinTest jt = ft.start(3);
		System.out.println(jt.getSum());
	}
	
}
