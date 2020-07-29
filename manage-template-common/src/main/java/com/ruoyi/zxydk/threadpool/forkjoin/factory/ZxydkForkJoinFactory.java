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
 * zyiyuan 3:34:33 PM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.factory;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ruoyi.zxydk.threadpool.forkjoin.interfaces.ZxydkFork;

@SuppressWarnings("all")
public class ZxydkForkJoinFactory  implements ApplicationContextAware , InitializingBean , Serializable {
	
	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = -5219285222663001983L;
	
	/** 日志 */
	public static Logger logger = LoggerFactory.getLogger(ZxydkForkJoinFactory.class);
	
	/** spring处理上下文 */
	private ApplicationContext context;
	
	/** spring手动注入bean工厂 */
	private AutowireCapableBeanFactory autowireFactory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO ?? 有些自定义的工厂前置设计，后续项目只要继承即可
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		this.context = applicationContext;
		
		this.autowireFactory = applicationContext.getAutowireCapableBeanFactory();
	}
	
	/**
	 * @category 构建领域模型中的实例 
	 * 
	 * @param clz [Class<T extends ZxydkFork>]
	 * 
	 * @return T [<T extends ZxydkFork>]
	 */
	public <T extends ZxydkFork> T newInstance( Class<T> clz ){
		try {
			T t = clz.getConstructor().newInstance();
			
			if( clz.isAnnotationPresent(com.ruoyi.zxydk.threadpool.forkjoin.annotation.ZxydkFork.class) ) {
				
				com.ruoyi.zxydk.threadpool.forkjoin.annotation.ZxydkFork  zxydkForkAnnotation = clz.getAnnotation(com.ruoyi.zxydk.threadpool.forkjoin.annotation.ZxydkFork.class);
				
				if( null != zxydkForkAnnotation) {
					t.setForkSplitHanlerPoint(zxydkForkAnnotation.splitHandle());
					this.autowireFactory.autowireBeanProperties(t, 0, false);
				}else {
					throw new RuntimeException("forkjoin - 工厂构建，未找到指定的annotation @zxydkFork！");
				}
			}
			return t;
		} catch (Exception e) {
			logger.error("forkjoin - 工厂构建:{}时，发生初始化异常，异常信息：{}！",clz, e);
			throw new RuntimeException( String.format("forkjoin - 工厂构建：%s,初始化异常！", clz) );
		} 
	}
	
	
	/**
	 * @category 构建领域模型中的实例 
	 * 
	 * @param clz [Class<T extends ZxydkFork>]
	 * 
	 * @return T [<T extends ZxydkFork>]
	 */
	public <T extends ZxydkFork , M extends Object> T newInstance( Class<T> clz , List<M> list ){
		 T t = this.newInstance(clz);
		 t.setTargetCollection(list);
		 return t;
	}
}
