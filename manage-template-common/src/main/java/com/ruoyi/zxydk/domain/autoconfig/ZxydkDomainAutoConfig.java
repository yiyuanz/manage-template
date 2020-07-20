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
 * zyiyuan 1:59:10 PM 创建
 */
package com.ruoyi.zxydk.domain.autoconfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ruoyi.zxydk.domain.autoconfig.propertis.ZxydkProperties;
import com.ruoyi.zxydk.domain.factory.ZxydkDomainFactory;


@SuppressWarnings("all")
@Configuration
@EnableConfigurationProperties({ZxydkProperties.class})
@ConditionalOnProperty(value = {"com.ruoyi.manage.template.autoconfig.enable"}, matchIfMissing = true)
public class ZxydkDomainAutoConfig implements ApplicationContextAware {

	@Autowired
	private ZxydkProperties zdkProperties;
	
	
	private ApplicationContext context;
	
	
	private AutowireCapableBeanFactory autowireFactory;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
		
		this.autowireFactory = this.context.getAutowireCapableBeanFactory();
	}
	
	
	/**
	 * @category 实体工厂 
	 */
	@Bean
	public ZxydkDomainFactory zxydkDomainFactory(){
		
		return  new ZxydkDomainFactory();
	}
	
}
