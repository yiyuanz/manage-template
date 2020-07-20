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
 * zyiyuan 4:01:27 PM 创建
 */
package com.ruoyi.zxydk.eventbus.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.dispatch.IHandlerInvocation;
import net.engio.mbassy.dispatch.MessageDispatcher;
import net.engio.mbassy.subscription.SubscriptionContext;

@SuppressWarnings("all")
public class ExMessageDispatcher  extends MessageDispatcher {
	
	public static final Logger logger = LoggerFactory.getLogger(ExMessageDispatcher.class);

	 private final IHandlerInvocation invocation;
	
	public ExMessageDispatcher(SubscriptionContext context, IHandlerInvocation invocation) {
		// TODO Auto-generated constructor stub
		
		super(context, invocation);
		
		this.invocation = invocation;
	} 
	
	@Override
    public void dispatch( final MessagePublication publication, final Object message, final Iterable listeners) {
        publication.markDispatched();
        
        for (Object listener : listeners) {
        	logger.info("listener:{} 收到消息:{}", listener.getClass().getSimpleName(), message);
        	
            getInvocation().invoke(listener, message, publication);
        }
    }

    @Override
    public IHandlerInvocation getInvocation() {
        return invocation;
    }
	
}
