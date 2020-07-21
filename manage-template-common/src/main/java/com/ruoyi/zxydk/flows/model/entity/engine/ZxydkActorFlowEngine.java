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
 * zyiyuan 11:03:28 AM 创建
 */
package com.ruoyi.zxydk.flows.model.entity.engine;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.ruoyi.zxydk.flows.model.arggreroot.chat.ZxydkActionFlow;
import com.ruoyi.zxydk.flows.model.arggreroot.chat.ZxydkActionFlow.Key;
import com.ruoyi.zxydk.flows.model.arggreroot.engine.ZxydkFlowEngine;

@SuppressWarnings("all")
public class ZxydkActorFlowEngine<T extends Object> implements ZxydkFlowEngine<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 481489443503707294L;
	
	
	private ApplicationContext context;
	
	
	private ZxydkActionFlow< T >  actionFlow;
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.context = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void manualRegistActionFlow(ZxydkActionFlow<T> actionFlow) {
		// ... 后期迭代升级手动配置后，自动注入到容器中 ...
		this.actionFlow = actionFlow;
	}

	@Override
	public void start(T object, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		this.actionFlow.action(object, vals);
	}

	@Override
	public void execute(String nodeName, T object, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		this.actionFlow.actionFromNode(nodeName, object, vals);
	}

	@Override
	public void confirmFlow(Key key) {
		// TODO Auto-generated method stub
		if( null != key && key.equals( this.actionFlow.getUniqueKey() ) ) {
			return;
		}
		throw new RuntimeException(String.format( " confirm flow fail , key : %s !", key) );
	}

	@Override
	public Key getUniqueKey() {
		// TODO Auto-generated method stub
		return this.actionFlow.getUniqueKey();
	}

	@Override
	public ZxydkActionFlow getFlowInfo() {
		// TODO Auto-generated method stub
		return this.actionFlow;
	}
	
}
