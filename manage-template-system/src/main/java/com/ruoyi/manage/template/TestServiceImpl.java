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
 * zyiyuan 2:31:43 PM 创建
 */
package com.ruoyi.manage.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ruoyi.system.flowtest.FlowEntity;
import com.ruoyi.zxydk.flows.services.ZxydkFlowDomainService;

@Service
public class TestServiceImpl implements TestService {

	
	@Autowired
	private ZxydkFlowDomainService flowDomainService;
	
	@Override
	public void say() {
		// TODO Auto-generated method stub
		FlowEntity fe = new FlowEntity();
      fe.setName("zhang23");
      flowDomainService.start("test", "1.0", fe, Maps.newHashMap());
	}
	
}
