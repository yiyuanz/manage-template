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
 * zyiyuan 11:42:17 AM 创建
 */
package com.ruoyi.zxydk.threadpool.forkjoin.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ForkTowardTypeEnum {
	
	LEFT_TO_WARD("LEFT_TO_WARD","向左分裂"),
	
	RIGHT_TO_WARD("RIGHT_TO_WARD" , "向右分裂"),
	;
	
	/**
	 * 枚举值
	 */
	private final String code;
	
	/**
	 * 枚举描述
	 */
	private final String message;
	
	/**
	 * @param code    枚举值
	 * @param message 枚举描述
	 */
	private ForkTowardTypeEnum(String code, String message) {
	    this.code = code;
	    this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
	    return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
	    return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
	    return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
	    return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return ForkTowardTypeEnum
	 */
	public static ForkTowardTypeEnum getByCode(String code) {
	    for (ForkTowardTypeEnum _enum : values()) {
	        if (_enum.getCode().equals(code)) {
	            return _enum;
	        }
	    }
	    return null;
	}
	
	/**
	 * 获取全部枚举
	 *
	 * @return List<ForkTowardTypeEnum>
	 */
	public static java.util.List<ForkTowardTypeEnum> getAllEnum() {
	    java.util.List<ForkTowardTypeEnum> list = new java.util.ArrayList<ForkTowardTypeEnum>(values().length);
	    for (ForkTowardTypeEnum _enum : values()) {
	        list.add(_enum);
	    }
	    return list;
	}
	
	/**
	 * 获取全部枚举值
	 *
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
	    java.util.List<String> list = new java.util.ArrayList<String>(values().length);
	    for (ForkTowardTypeEnum _enum : values()) {
	        list.add(_enum.code());
	    }
	    return list;
	}
	
	/**
	 * 通过code获取msg
	 *
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
	    if (code == null) {
	        return null;
	    }
	    ForkTowardTypeEnum _enum = getByCode(code);
	    if (_enum == null) {
	        return null;
	    }
	    return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 *
	 * @param _enum
	 * @return
	 */
	public static String getCode(ForkTowardTypeEnum _enum) {
	    if (_enum == null) {
	        return null;
	    }
	    return _enum.getCode();
	}
	
	/**
	 * 实名登记map
	 *
	 * @return
	 */
	public static Map<String, String> maps() {
	    Map<String, String> map = Maps.newLinkedHashMap();
	    for (ForkTowardTypeEnum type : getAllEnum()) {
	        map.put(type.getCode(), type.getMessage());
	    }
	    return map;
	}
}
