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
 * zyiyuan 9:31:43 AM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.interfaces;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.ApplicationContextAware;

@SuppressWarnings("all")
public interface ZxydkFork< T , V extends ZxydkJoin> extends ApplicationContextAware, Serializable{
	
	/**
	 * @category 设置目标执行对象 
	 * 
	 * @param Collection con
	 * 
	 */
	public void setTargetCollection( List<T> con );
	
	/**
	 * @category 设置目标分裂点
	 * 
	 * @param Collection con
	 * 
	 */
	public void setForkSplitHanlerPoint( int handlePoint );
	
	/**
	 *  @category 初始化返回值
	 * 
	 */
	public V initJoinValue();
	
	/**
	 * @category 启动 
	 */
	public V start(int parallelism);
	
}
