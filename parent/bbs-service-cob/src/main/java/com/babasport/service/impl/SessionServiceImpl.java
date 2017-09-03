package com.babasport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babasport.service.SessionService;

import redis.clients.jedis.Jedis;

/**
 * 自定义session服务实现类
 * 
 * @author vian
 *
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService{

	@Autowired
	private Jedis jedis;
	
	@Override
	public void addUsernameToRedis(String key, String value) {
		// 因为考虑到以后可能还有验证码，所以在key后面加个username再保存到redis中
		jedis.set(key+":username", value);
		//设置失效时间（必要）
		jedis.expire(key+":username", 1800);
		
	}

	@Override
	public String getUsernameFromRedis(String key) {
		String username = jedis.get(key+":username");
		if (username!=null) {
			//重置失效时间
			jedis.expire(key+":username", 1800);
		}
		return username;
	}

}
