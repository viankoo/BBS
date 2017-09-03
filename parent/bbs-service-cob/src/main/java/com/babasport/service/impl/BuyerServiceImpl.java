package com.babasport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babasport.mapper.BuyerMapper;
import com.babasport.pojo.Buyer;
import com.babasport.service.BuyerService;

/**
 * 
 * 买家服务实现类
 * @author vian
 *
 */
@Service("buyerService")
public class BuyerServiceImpl implements BuyerService{

	@Autowired
	private BuyerMapper buyerMapper;
	
	@Override
	public Buyer findByUsername(String username) {
		Buyer buyer = new Buyer();
		buyer.setUsername(username);
		Buyer one = buyerMapper.selectOne(buyer);
		
		return one;
	}

}
