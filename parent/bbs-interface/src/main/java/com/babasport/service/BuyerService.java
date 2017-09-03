package com.babasport.service;

import com.babasport.pojo.Buyer;

/**
 * 买家服务接口
 * @author vian
 *
 */
public interface BuyerService {

	
	/**
	 * 通过买家用户名，查询买家
	 * @param username
	 * @return
	 */
	public Buyer findByUsername(String username);
	
}
