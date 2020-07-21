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
 * zyiyuan 1:45:13 PM 创建
 */
package com.ruoyi.system.flowtest;

import java.util.Map;

import com.ruoyi.zxydk.flows.annotation.interfaces.ZxydkFlowChat;
import com.ruoyi.zxydk.flows.annotation.interfaces.ZxydkFlowNode;
import com.ruoyi.zxydk.flows.annotation.interfaces.ZxydkFlowTransition;
import com.ruoyi.zxydk.flows.constants.ZxydkFlowContants;
import com.ruoyi.zxydk.flows.model.entity.chat.actor.ZxydkActionAbstractNode;


@ZxydkFlowChat(name = "test" , version = "1.0")
@ZxydkFlowNode(name = "step1" , 
			   conditions = {
			      @ZxydkFlowTransition(to = ZxydkFlowContants.FLOW_SIGN_OF_END_ACTOR_NODE , desc = "end" , event = ZxydkFlowContants.FLOW_SIGN_OF_END_ACTOR_NODE) 
			   })
public class FlowStep1 extends ZxydkActionAbstractNode<FlowEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3143098613758326777L;

	@Override
	public String execute(FlowEntity target, Map<String, Object> vals) {
		// TODO Auto-generated method stub
		System.out.println("step1:execute" + target);
		return ZxydkFlowContants.FLOW_SIGN_OF_END_ACTOR_NODE;
	}

	@Override
	public void before(FlowEntity target, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		super.before(target, vars);
		System.out.println("step1:before" + target);
	}

	@Override
	public void after(FlowEntity target, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		super.after(target, vars);
		System.out.println("step1:after" + target);
	}
	
	
	
}
