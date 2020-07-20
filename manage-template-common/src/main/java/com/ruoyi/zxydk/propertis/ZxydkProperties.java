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
 * zyiyuan 下午5:40:54 创建
 */
package com.ruoyi.zxydk.propertis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;


/**
 * @category 自定义zdk的组件所需配置 
 * 
 * 
 */
@ConfigurationProperties( prefix = "com.ruoyi.manage.template.autoconfig" )
public class ZxydkProperties implements InitializingBean {

	public static final String PREFIX = "com.ruoyi.manage.template.autoconfig";
	
	/** 是否打开自动配置 */
	private boolean enable = true;
	
	/** 线程池-最小通道 ： 默认10 */
	private int threadMin = 10;
	 
	/** 线程池-最大通道 ： 默认100 */
	private int threadMax = 100;
	 
	/** 线程池-等待队列 ： 默认50 */
	private int threadQueue = 50;
	 
	
	
//	/** 
//	 * 流程引擎 流程图定义的路径 （可自定义修改） 
//	 * <pre>
//	 * 	 支持多路径的配置，通过";"隔离
//	 * </pre> 
//	 */
//	private String flowFiles ="classpath*:/flow/**/*.xml";
	
//	/** SOA分布式系统结构下， 系统模块名 */
//	private String  moduleName;
	
//	public String getFlowFiles() {
//		return flowFiles;
//	}
//
//	public void setFlowFiles(String flowFiles) {
//		this.flowFiles = flowFiles;
//	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	

//	public String getModuleName() {
//		return moduleName;
//	}
//
//	public void setModuleName(String moduleName) {
//		this.moduleName = moduleName;
//	}
	
	public int getThreadMin() {
		return threadMin;
	}

	public void setThreadMin(int threadMin) {
		this.threadMin = threadMin;
	}

	public int getThreadMax() {
		return threadMax;
	}

	public void setThreadMax(int threadMax) {
		this.threadMax = threadMax;
	}

	public int getThreadQueue() {
		return threadQueue;
	}

	public void setThreadQueue(int threadQueue) {
		this.threadQueue = threadQueue;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof ZxydkProperties)) {
			return false;
		} else {
			ZxydkProperties other = (ZxydkProperties) o;
			if (!other.canEqual(this)) {
				return false;
			} else if (this.isEnable() != other.isEnable()) {
				return false;
			}  else {
				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof ZxydkProperties;
	}

	public String toString() {
		return "Properties(enable=\"" + this.isEnable() + ", threadMin=" + this.getThreadMin() + ", threadMax=" + ",threadQueue=" + this.getThreadQueue() ;
	}
 
	
	/**
	 * @throws Exception
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
//		if (this.enable) {
//			Assert.hasText(this.moduleName, "执行容器必须指定模块名称");
//		}
	}
}
