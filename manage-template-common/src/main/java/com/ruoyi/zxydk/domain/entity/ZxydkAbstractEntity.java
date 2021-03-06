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
 * zyiyuan 11:42:42 AM 创建
 */
package com.ruoyi.zxydk.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.zxydk.domain.factory.ZxydkDomainFactory;
import com.ruoyi.zxydk.eventbus.bus.ZxydkEventBus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@SuppressWarnings("all")
public abstract class ZxydkAbstractEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 * 
	 */
	private static final long serialVersionUID = 4279359338455605164L;
	
	/**
	 * mysql 设定的id 自增 并为主键
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Autowired
	private ZxydkDomainFactory domainFactory;
	
	@Autowired
	private ZxydkEventBus eventBus;
	
	/**
	 * @category 事务提交后 发送事件  （可同步 、 异步处理） 
	 */
	public void publishAfterTransactionCommitted() {
		this.eventBus.publishAfterTransactionCommitted(this);
	}
	
	/**
	 * @category 普通发送事件  （可同步 、 异步处理） 
	 */
	public void publish() {
		this.eventBus.publish(this);
	}
	
	
	
	
	 
}
