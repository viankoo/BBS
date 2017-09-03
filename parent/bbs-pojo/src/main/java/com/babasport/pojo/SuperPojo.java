package com.babasport.pojo;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * 通用实体类，接受参数没有任何限制，可以用于装载多表连接查询返回的复合数据
 * 
 * @author vian
 *
 */
public class SuperPojo extends TreeMap<String, Object> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 设置实体类属性
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 已经设置好的实体类本身,实现连点设置属性效果
	 */
	public SuperPojo setProperty(String name, Object value) {
		this.put(name, value);
		return this;
	}

}
