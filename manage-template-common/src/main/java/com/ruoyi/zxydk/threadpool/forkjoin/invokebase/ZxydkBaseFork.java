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
 * zyiyuan 9:38:54 AM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.invokebase;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.google.common.collect.Lists;
import com.ruoyi.zxydk.common.reflects.Reflections;
import com.ruoyi.zxydk.threadpool.forkjoin.enums.ForkTowardTypeEnum;
import com.ruoyi.zxydk.threadpool.forkjoin.interfaces.ZxydkFork;
import com.ruoyi.zxydk.threadpool.forkjoin.interfaces.ZxydkJoin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@SuppressWarnings("all")
public abstract class ZxydkBaseFork<T extends Object , V extends ZxydkJoin> extends RecursiveTask<V> implements ZxydkFork<T , V> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1229985707057420380L;
	
	// 日志
	protected static final Logger logger =LoggerFactory.getLogger(ZxydkBaseFork.class);	
	// 分裂点
	protected int splitPoint;
	// 目标链
	protected List<T> targetCons;
	// spring context
	protected ApplicationContext context;
	// beanFactory
	protected AutowireCapableBeanFactory factory;

	public ZxydkBaseFork() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ZxydkBaseFork(int splitPoint, List<T> targetCons) {
		super();
		this.splitPoint = splitPoint;
		this.targetCons = targetCons;
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.context = applicationContext;
		this.factory = this.context.getAutowireCapableBeanFactory();
	}
	
	/**
	 * @category 每个循环需要执行的操作 
	 * 
	 */
	protected abstract void eachExecute(T tc) ;
	
	@Override
	public void setTargetCollection(List<T> con) {
		// TODO Auto-generated method stub
		this.targetCons = con;
	}

	@Override
	public V initJoinValue() {
		 try {
			Class<V> zxydkJoinType = Reflections.getSuperClassGenricType(getClass(), 1);
			V v = zxydkJoinType.newInstance();
			if( null != this.factory ) {
				this.factory.autowireBeanProperties(v, 0, false);
			}
			return v;
		} catch (Exception e) {
			logger.error("初始化 - zxydkjoin - 结果失败！ 异常：{}", e);
			throw new RuntimeException("初始化 - zxydkjoin - 结果失败！");
		}
	}

	@Override
	public void setForkSplitHanlerPoint(int handlePoint) {
		// TODO Auto-generated method stub
		this.splitPoint = handlePoint;
	}

	@Override
	protected V compute() {
		Boolean hasDiv = this.targetCons.size() > this.splitPoint;
		if( hasDiv ) {
			// 分裂
			int middleDiv = Math.round(this.targetCons.size() / 2);
			ZxydkBaseFork<T ,V> leftFork = divisionConstructor(middleDiv , ForkTowardTypeEnum.LEFT_TO_WARD);
			leftFork.fork();
			ZxydkBaseFork<T ,V> rightFork = divisionConstructor(middleDiv ,  ForkTowardTypeEnum.RIGHT_TO_WARD );
			rightFork.fork();
			return (V) leftFork.join().merge(rightFork.join());
		}else {
			// 不分裂
			this.setRawResult(initJoinValue());
			this.targetCons.forEach( tc -> eachExecute( tc ) );
			return this.getRawResult();
		}
	}
	
	@Override
	public V start(int parallelism) {
		try {
			ForkJoinPool pool = new ForkJoinPool(parallelism);
			System.out.println("pool.getPoolSize():" + pool.getPoolSize());
			return pool.submit(this).get();
		}catch( Exception ex ) {
			logger.error("执行{} task 时，发生异常 ， 异常：{}" , ex);
			throw new RuntimeException(String.format("执行{%s} task 时，发生异常!", this.getClass().getName()));
		}
	}
	
	/**
	 * @category 分裂的构造器
	 * 
	 */
	protected <F extends ZxydkBaseFork<T ,V>>  F divisionConstructor(int middleDiv, ForkTowardTypeEnum forkToward) {
		try {
			F fork = (F) this.getClass().newInstance();
			BeanUtils.copyProperties(this, fork);
			/** 清空目标链 */ 
			fork.setTargetCollection(Lists.newArrayList());
			if(ForkTowardTypeEnum.LEFT_TO_WARD == forkToward) {
				// 向左分裂
				for( int i = 0 ; i <= middleDiv; i ++  ) {
					fork.getTargetCons().add(this.getTargetCons().get(i));
				}
			}else {
				// 向右分裂
				for(int i = middleDiv + 1 ; i < this.getTargetCons().size() ; i ++ ) {
					fork.getTargetCons().add(this.getTargetCons().get(i));
				}
			}
			if( null != this.factory ) {
				this.factory.autowireBeanProperties(fork, 0, false);
			}
			return fork;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("初始化分裂子任务时 - zxydkFork - 失败！ 异常：{}", e);
			throw new RuntimeException("初始化分裂子任务时 - zxydkFork - 结果失败！");
		
		}
		
	}
}
