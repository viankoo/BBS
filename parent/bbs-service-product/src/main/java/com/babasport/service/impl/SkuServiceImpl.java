package com.babasport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.babasport.mapper.SkuMapper;
import com.babasport.pojo.Sku;
import com.babasport.service.SkuService;
import com.github.abel533.entity.Example;

/**
 * 商品明细服务实现类
 * @author vian
 *
 */
@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService{

	@Autowired
	private SkuMapper skuMapper;
	
	@Override
	public List<Sku> findByPid(Long productId) {
		Example example = new Example(Sku.class);
		example.createCriteria().andEqualTo("productId", productId);
		List<Sku> skus = skuMapper.selectByExample(example);
		return skus;
	}

	@Override
	public List<Sku> findByPid1(Long productId) {
		List<Sku> skus = skuMapper.findByPid(productId);
		return skus;
	}

	
	@Override
	public int updateSkuById(Sku sku) {
		int i = skuMapper.updateByPrimaryKeySelective(sku);
		System.out.println(i);
		return i;
	}

}
