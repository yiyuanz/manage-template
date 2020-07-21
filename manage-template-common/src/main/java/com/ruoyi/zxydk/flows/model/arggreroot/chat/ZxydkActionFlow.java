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
 * zyiyuan 9:23:20 AM 创建
 */
package com.ruoyi.zxydk.flows.model.arggreroot.chat;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;

import com.ruoyi.zxydk.flows.model.arggreroot.chat.base.ZxydkFlowBaseAction;
import com.ruoyi.zxydk.flows.model.entity.chat.element.ZxydkActionNode;

/**
 * @category 流程图 
 * 
 */
public interface ZxydkActionFlow<T extends Object> extends ZxydkFlowBaseAction<T> , ApplicationContextAware , InitializingBean {
	
	/**************************************- 流程图 - 矢量坐标 - （ name, version ）********************************************************************************/
	/**
	 *  @category 设置流程图的唯一矢量
	 * 
	 *  @param ActionFlow.Key key 【流程图唯一键】
	 * 
	 */
	public void setUniqueKey( ZxydkActionFlow.Key key);
	
	/**
	 *  @category  获得流程图的唯一矢量
	 * 
	 *  @return ZxydkActionFlow.Key key 【流程图唯一键】
	 * 
	 */
	public ZxydkActionFlow.Key getUniqueKey();
	
	
	/**************************************- 流程图 - 业务执行的全局事务开关 -********************************************************************************/
	/**
	 *  @category  设置全局事务
	 * 
	 *  @param Boolean 
	 * 
	 */
	public void setHasOpenTransaction( Boolean isOpen );
	
	/**
	 * @category  获得全局事务标识
	 * 
	 * @return Boolean
	 * 
	 */
	public Boolean getHasOpenTransaction();
	
	/**************************************- 流程图 - 业务执行的流程日志开关 -********************************************************************************/
	/**
	 *  @category  设置全局跟踪日志
	 * 
	 *  @param Boolean 
	 * 
	 */
	public void setHasOpenLogger( Boolean isOpen );
	
	/**
	 * @category  获得全局跟踪日志
	 * 
	 * @return Boolean
	 * 
	 */
	public Boolean getHasOpenLogger();
	
	
	/**************************************- 流程图 - 转换方法 - 转化为矢量图 -********************************************************************************/
	/**
	 * @category  图中，所有节点建立关系，变成矢量图。
	 * 
	 */
	public void reflushTransaction();
	
	
	/**************************************- 流程图 - 添加节点 -********************************************************************************/
	/**
	 * @category  添加 - 流程图
	 * 
	 * @param actionNode [ZxydkActionNode<T>] 流程节点
	 * 
	 */
	public void addActionNode( ZxydkActionNode<T> actionNode );
	
	
	/**************************************- 流程图 - 实时执行节点内容 -********************************************************************************/
	/**
	 * @category  流程执行  从某个节点执行
	 * 
	 * @param nodeName 流程图中某个节点
	 * 
	 * @param target [T] 
	 * 
	 * @param vals [Map<String,Object>]
	 * 
	 */
	public void actionFromNode( String nodeName,  T target , Map<String, Object> vals );
	
	
	/**
	 *  @category 流程图 - 矢量图 坐标类定义 （name, version）
	 *  
	 */
	public static class Key{
		
		private String name;
		
		private String version;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		/**
		 * @param name
		 * @param version
		 */
		public Key(String name, String version) {
			super();
			this.name = name;
			this.version = version;
		}

		/**
		 * @param obj
		 * @return
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if( null == obj || this.getClass() != obj.getClass()){
				return Boolean.FALSE;
			}
			if( this == obj ){
				return Boolean.TRUE;
			}
			 Key key = (Key)obj;
			 if( !this.version.equals(key.version) ){
				 return Boolean.FALSE;
			 }
			 return null != key.getName()&& null != this.name && this.name.equals(key.getName());
		}

		/**
		 * @return
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			int result = this.name != null ? this.name.hashCode() : 0;
			result = 31 * result + (int)Math.round(Double.parseDouble(this.version));
			return result;
		}
		
	}
}
