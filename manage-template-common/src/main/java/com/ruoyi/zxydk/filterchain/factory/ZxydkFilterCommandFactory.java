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
 * zyiyuan 2:40:33 PM 创建
 */
package com.ruoyi.zxydk.filterchain.factory;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.support.TransactionTemplate;

import com.ruoyi.zxydk.filterchain.interfaces.FilterCommandChain;
import com.ruoyi.zxydk.filterchain.interfaces.Filtercommand;
import com.ruoyi.zxydk.filterchain.invoke.FilterCommondChainInvoke;

@SuppressWarnings("all")
public class ZxydkFilterCommandFactory implements ApplicationContextAware, InitializingBean , Serializable{

	/**
	 * 
	 * serialVersionUID
	 * 
	 */
	private static final long serialVersionUID = -8202477199738182780L;
	
	// 日志
		private static Logger logger = LoggerFactory.getLogger(ZxydkFilterCommandFactory.class);
	
	/** spring处理上下文 */
	private ApplicationContext context;
	
	/** spring手动注入bean工厂 */
	private AutowireCapableBeanFactory autowireFactory;

	@Autowired
	private TransactionTemplate template;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(  "-------- filter - command - factory  init finished ! -------" );
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
		this.autowireFactory = this.context.getAutowireCapableBeanFactory();
	}
	
	/**************************************************instance of filter-command ********************************************************************************************************/
	
	/**
	 * @category create command instance by calssloader reflact
	 * 
	 * @param Class<T> tclz  class of command or command sub type ...  
	 *  
	 * @param Boolean isOpenTransaction  transaction param
	 * 
	 * @param int grade  command order by grade asc
	 * 
	 * @return  T    class of command or command sub type ...
	 * 
	 */
	@Deprecated
	public <T extends Filtercommand > T createInstance(	Class<T> tclz, Boolean isOpenTransaction, int grade) {
		try {
			//获取有参构造
			Constructor<T> c = tclz.getConstructor();
			T t = c.newInstance();
			t.setOpenTransAction( isOpenTransaction );
			t.appoitOrder(grade);
			this.autowireFactory.autowireBeanProperties(t, 0, false);
			return t;
		} catch (NoSuchMethodException e) {
			logger.error("责任链构建指令时，未找到构建指令的构造器函数！class:{}!, ex:{}", tclz, e);
			throw new RuntimeException("责任链构建指令时，未找到构建指令的构造器函数!");
		} catch (SecurityException ex) {
			logger.error("责任链构建指令时，发生JDK的安全异常！class:{}!, ex:{}", tclz, ex);
			throw new RuntimeException("责任链构建指令时，发生JDK的安全异常！");
		}catch (InstantiationException iex) {
			logger.error("责任链构建指令时，发生发射失败异常！class:{}!, ex:{}", tclz, iex );
			throw new RuntimeException("责任链构建指令时，发生发射失败异常！");
		} catch (IllegalAccessException lex) {
			logger.error("责任链构建指令时，发生参数验证失败异常！class:{}!, ex:{}", tclz, lex );
			throw new RuntimeException("责任链构建指令时，发生参数验证失败异常！");
		} catch (IllegalArgumentException iaex) {
			logger.error("责任链构建指令时，发生参数转换失败异常！class:{}!, ex:{}", tclz, iaex );
			throw new RuntimeException("责任链构建指令时，发生参数转换失败异常！");
		} catch (InvocationTargetException itaex) {
			logger.error("责任链构建指令时，发生运行时参数异常！class:{}!, ex:{}", tclz, itaex );
			throw new RuntimeException("责任链构建指令时，发生运行时参数异常！");
		}catch( Exception allex ){
			logger.error("责任链构建指令时，发生未知业务异常！class:{}!, ex:{}", tclz, allex );
			throw new RuntimeException("责任链构建指令时，发生未知业务异常！");
		}
	}
	
	/**
	 * @category create command instance with self annotation by classloader reflact
	 * <pre> 配合自定义的注解 联合使用 </pre>
	 * 
	 * 
	 * @param Class<T> tclz  
	 *  
	 * @return  T    class of command or command sub type ...
	 * 
	 */
	public < T extends Filtercommand > T createInstanceWithAnnotation(Class<T> tclz) {
		if( tclz.isAnnotationPresent(com.ruoyi.zxydk.filterchain.annotation.CommandOrder.class) ) {
			logger.error("责任链构建指令时，使用默认构造器时，class:{} 缺失CommandOrder的定义！", tclz);
			throw new RuntimeException("责任链构建指令时，使用默认构造器时，缺失CommandOrder的定义");
		}
		com.ruoyi.zxydk.filterchain.annotation.CommandOrder filterCommandOrder = tclz.getAnnotation(com.ruoyi.zxydk.filterchain.annotation.CommandOrder.class);
		if( filterCommandOrder.order() < 0 ) {
			logger.error("责任链构建指令时，使用默认构造器时，class:{} 缺失order：顺序的定义！", tclz);
			throw new RuntimeException("责任链构建指令时，使用默认构造器时，缺失order：顺序的定义");
		}
		return this.createInstance(tclz, filterCommandOrder.isOpenSubTransaction(), filterCommandOrder.order());
		
	}
	
	
	/**************************************************instance of filter-command-chain ********************************************************************************************************/
	/**
	 * @category 创建责任链 - 关闭全局事务 
	 */
	public FilterCommandChain newChainInstance() {
		FilterCommandChain chain = new FilterCommondChainInvoke();
		chain.setTransactionTemplate(template);
		return chain;
	}
	
	/**
	 * @category 创建责任链 - 开启全局事务 
	 */
	public FilterCommandChain newChainInstranceWithTransaction() {
		FilterCommandChain chain = this.newChainInstance();
		chain.setTransactionSign(Boolean.TRUE);
		return chain;
	}
}
