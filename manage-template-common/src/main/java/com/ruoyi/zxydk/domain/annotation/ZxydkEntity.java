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
 * zyiyuan 1:55:37 PM 创建
 */
package com.ruoyi.zxydk.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value={java.lang.annotation.ElementType.TYPE})
public @interface ZxydkEntity {
	
	/**  是否默认打开自动配置  */
	public boolean isOpenAutowired() default true;
	
	/** 描述 */
	public String description() default "";
}
