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
 * zyiyuan 4:00:58 PM 创建
 */
package com.ruoyi.zxydk.eventbus.bus;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.publication.SyncAsyncPostCommand;

public class ZxydkEventBus<T> extends MBassador<T> implements InitializingBean {

	private TransactionTemplate transactionTemplate;
	
	
	public static final Logger logger = LoggerFactory.getLogger(ZxydkEventBus.class);

	public ZxydkEventBus() {
    }

    public ZxydkEventBus(IPublicationErrorHandler errorHandler) {
        super(errorHandler);
    }

    public ZxydkEventBus(IBusConfiguration configuration) {
        super(configuration);
    }
	
    @Autowired(required = false)
    private PlatformTransactionManager platformTransactionManager;
    
    
    /**
     * <pre> ... 同步/异步 ... </pre> 
     */
    @Override
    public IMessagePublication publish(T message) {
    	logger.info("发送事件:{}", message);
        return super.publish(message);
    }
    
    /**
     * <pre> ... 同步/异步 ... </pre> 
     */
    @Override
    public SyncAsyncPostCommand<T> post(T message) {
    	logger.info("发送事件:{}", message);
        return super.post(message);
    }
    
    /**
     * <pre> ... 异步 ... </pre> 
     */
    @Override
    public IMessagePublication publishAsync(T message) {
    	logger.info("发送事件:{}", message);
        return super.publishAsync(message);
    }

    /**
     * <pre> ... 异步 ... </pre> 
     */
    @Override
    public IMessagePublication publishAsync(T message, long timeout, TimeUnit unit) {
    	logger.info("发送事件:{}", message);
        return super.publishAsync(message, timeout, unit);
    }
    
    /**
     * 仅当当前事务提交成功后才发布消息,非事务环境直接发布消息
     */
    public void publishAfterTransactionCommitted(T message) {
        if (TransactionSynchronizationManager.isSynchronizationActive()
                && transactionTemplate != null) {
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCompletion(int status) {
                            if (status == TransactionSynchronization.STATUS_COMMITTED) {
                                transactionTemplate.execute(
                                        status1 -> {
                                            ZxydkEventBus.this.publish(message);
                                            return null;
                                        });
                            }
                        }
                    });
        } else {
        	ZxydkEventBus.this.publish(message);
        }
    }
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		 if (platformTransactionManager != null) {
	            DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
	            defaultTransactionDefinition.setPropagationBehavior( TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	            transactionTemplate =  new TransactionTemplate(platformTransactionManager, defaultTransactionDefinition);
	        } else {
	            logger.warn("spring事务管理器不存在，publishAfterTransactionCommitted方法不会绑定到事务中!");
	        }
	}
}
