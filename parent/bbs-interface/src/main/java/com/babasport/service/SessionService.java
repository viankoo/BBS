package com.babasport.service;

/**
 * 
 * session服务接口
 * @author vian
 *
 */
public interface SessionService {

	/**
	 * 把自定义session存放到redis中
	 * @param key uuid
	 * @param value
	 */
	public void addUsernameToRedis(String key,String value);
	
	
	
	/**
	 * 从redis中获取session
	 * @param key
	 * @return
	 */
	public String getUsernameFromRedis(String key);
	
	
}
