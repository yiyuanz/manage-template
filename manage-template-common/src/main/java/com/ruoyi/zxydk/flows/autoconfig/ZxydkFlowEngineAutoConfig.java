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
 * zyiyuan 11:36:24 AM 创建
 */
package com.ruoyi.zxydk.flows.autoconfig;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.zxydk.domain.autoconfig.ZxydkDomainAutoConfig;
import com.ruoyi.zxydk.eventbus.autoconfig.ZxydkEventAutoConfig;
import com.ruoyi.zxydk.flows.repertory.chat.ZxydkFlowChatElementCreator;
import com.ruoyi.zxydk.flows.services.ZxydkFlowDomainService;
import com.ruoyi.zxydk.flows.services.impls.ZxydkFlowDomainServiceImpl;
import com.ruoyi.zxydk.propertis.ZxydkProperties;

@Order(value = 0)
@SuppressWarnings("all")
@Configuration
@EnableConfigurationProperties({ZxydkProperties.class})
@ConditionalOnProperty(value = {"com.ruoyi.manage.template.autoconfig.enable"}, matchIfMissing = true)
@AutoConfigureAfter(value= {ZxydkDomainAutoConfig.class})
public class ZxydkFlowEngineAutoConfig implements ApplicationContextAware {

	// 日志
	public static final Logger logger = LoggerFactory.getLogger(ZxydkEventAutoConfig.class);
	
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
	 * @category 流程引擎 流程元素构建器
	 */
	@Bean
	public ZxydkFlowChatElementCreator zxydkFlowChatElementCreator() {
		
		return new ZxydkFlowChatElementCreator();
	}
	
	/**
	 * @category 流程引擎  领域服务
	 */
	@Bean
	public ZxydkFlowDomainService zxydkFlowDomainService() throws IOException{
		
		return new ZxydkFlowDomainServiceImpl( );
	}
	
}
