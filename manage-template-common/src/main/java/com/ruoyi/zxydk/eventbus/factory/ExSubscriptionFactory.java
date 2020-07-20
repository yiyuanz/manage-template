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
 * zyiyuan 4:01:52 PM 创建
 */
package com.ruoyi.zxydk.eventbus.factory;


import com.ruoyi.zxydk.eventbus.dispatcher.ExMessageDispatcher;

import net.engio.mbassy.dispatch.EnvelopedMessageDispatcher;
import net.engio.mbassy.dispatch.FilteredMessageDispatcher;
import net.engio.mbassy.dispatch.IHandlerInvocation;
import net.engio.mbassy.dispatch.IMessageDispatcher;
import net.engio.mbassy.subscription.SubscriptionContext;
import net.engio.mbassy.subscription.SubscriptionFactory;

@SuppressWarnings("all")
public class ExSubscriptionFactory extends SubscriptionFactory {
	
	
    protected IMessageDispatcher buildDispatcher(SubscriptionContext context, IHandlerInvocation invocation) {
    	
	     IMessageDispatcher dispatcher = new ExMessageDispatcher(context, invocation);
	    
	     if (context.getHandler().isEnveloped()) {
	         dispatcher = new EnvelopedMessageDispatcher(dispatcher);
	     }
	     
	     if (context.getHandler().isFiltered()) {
	         dispatcher = new FilteredMessageDispatcher(dispatcher);
	     }
	     
	     return dispatcher;
	 }
}