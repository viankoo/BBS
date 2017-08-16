package com.babasport.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.babasport.mapper.TestTbMapper;
import com.babasport.pojo.TestTb;
import com.babasport.service.TestTbService;

/**
 * 测试服务类
 * @author vian
 *
 */
@Service("testTbService")
@Transactional
public class TestTbServiceImpl implements TestTbService{

	@Autowired
	private TestTbMapper testTbMapper;
	
	@Override
	public void addTestTb(TestTb testTb) {
		testTbMapper.add(testTb);
//		int i = 1/0;
//		TestTb testTb2 = new TestTb();
//		testTb2.setName("xxx10");
//		testTb2.setBirthday(new Date());
//		testTbMapper.add(testTb2);
	}

}
