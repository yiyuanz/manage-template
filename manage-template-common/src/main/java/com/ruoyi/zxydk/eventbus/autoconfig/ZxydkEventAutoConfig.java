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
 * zyiyuan 4:16:15 PM 创建
 */
package com.ruoyi.zxydk.eventbus.autoconfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ruoyi.zxydk.eventbus.annotation.ZxydkEventHandler;
import com.ruoyi.zxydk.eventbus.bus.ZxydkEventBus;
import com.ruoyi.zxydk.eventbus.factory.ExSubscriptionFactory;
import com.ruoyi.zxydk.propertis.ZxydkProperties;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.subscription.SubscriptionManagerProvider;


@SuppressWarnings("all")
@Configuration
@EnableConfigurationProperties({ZxydkProperties.class})
@ConditionalOnProperty(value = {"com.ruoyi.manage.template.autoconfig.enable"}, matchIfMissing = true)
public class ZxydkEventAutoConfig  implements ApplicationContextAware {

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
	
	
	@Bean
    public ZxydkEventBus messageBus(@Qualifier("commonTaskExecutor") ThreadPoolTaskExecutor poolTaskExecutor) {
        Feature.AsynchronousHandlerInvocation asynchronousHandlerInvocation =
                new Feature.AsynchronousHandlerInvocation();
        asynchronousHandlerInvocation.setExecutor(poolTaskExecutor.getThreadPoolExecutor());
        Feature.SyncPubSub syncPubSub =
                new Feature.SyncPubSub()
                        .setMetadataReader(new MetadataReader())
                        .setPublicationFactory(new MessagePublication.Factory())
                        .setSubscriptionFactory(new ExSubscriptionFactory())
                        .setSubscriptionManagerProvider(new SubscriptionManagerProvider());
        ZxydkEventBus bus =
                new ZxydkEventBus(
                        new BusConfiguration()
                                .addFeature(syncPubSub)
                                .addFeature(asynchronousHandlerInvocation)
                                .addFeature(Feature.AsynchronousMessageDispatch.Default())
                                .addPublicationErrorHandler(
                                        error -> {
                                            Method handler = error.getHandler();
                                            String name =
                                                    handler.getDeclaringClass().getSimpleName() + "#" + handler.getName();
                                            Throwable throwable = error.getCause();
                                            if (throwable instanceof InvocationTargetException) {
                                                throwable = ((InvocationTargetException) throwable).getTargetException();
                                            }
                                            logger.error("调用方法:{} 失败，异常为：", name, throwable);
                                        })
                                .setProperty(IBusConfiguration.Properties.BusId, "global bus"));
        return bus;
    }

	@Bean
    public ThreadPoolTaskExecutor commonTaskExecutor(ZxydkProperties properties) {
        ThreadPoolTaskExecutor bean = new ThreadPoolTaskExecutor();
        bean.setCorePoolSize(properties.getThreadMin());
        bean.setMaxPoolSize(properties.getThreadMax());
        bean.setQueueCapacity(properties.getThreadQueue());
        bean.setKeepAliveSeconds(300);
        bean.setWaitForTasksToCompleteOnShutdown(true);
        bean.setAllowCoreThreadTimeOut(true);
        bean.setThreadNamePrefix("common-thread-pool-");
        bean.setTaskDecorator(new LogTaskDecorator());
        bean.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return bean;
    }
	
	@Slf4j
    public static class LogTaskDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            Runnable newR =
                    () -> {
                        if (copyOfContextMap != null) {
                            MDC.setContextMap(copyOfContextMap);
                        }
                        try {
                            runnable.run();
                        } catch (Exception e) {
                            log.error("线程池任务处理异常", e);
                        } finally {
                            if (copyOfContextMap != null) {
                                MDC.clear();
                            }
                        }
                    };
            return newR;
        }
    }
	
	@Configuration
    public static class EventHandlerConfig implements ApplicationContextAware{
		
        @Autowired
        private ZxydkEventBus eventBus;
        
        private ApplicationContext context;

        @PostConstruct
        public void afterPropertiesSet() throws Exception {
            Map<String, Object> beansWithAnnotation = 
                    context.getBeansWithAnnotation(ZxydkEventHandler.class);
            for (Object o : beansWithAnnotation.values()) {
                eventBus.subscribe(o);
                logger.info("注册事件处理器:{}", o.getClass().getName());
            }
        }

        @PreDestroy
        public void destroy() throws Exception {
            Map<String, Object> beansWithAnnotation =
                    context.getBeansWithAnnotation(ZxydkEventHandler.class);
            for (Object o : beansWithAnnotation.values()) {
                eventBus.unsubscribe(o);
                logger.info("销毁事件处理器:{}", o.getClass().getName());
            }
        }

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			// TODO Auto-generated method stub
			this.context = applicationContext;
			
			System.out.println("---init zxydk-event-handler- finished!----");
		}
    }
	
}
