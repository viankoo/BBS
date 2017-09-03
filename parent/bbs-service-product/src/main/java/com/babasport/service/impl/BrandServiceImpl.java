package com.babasport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babasport.mapper.BrandMapper;
import com.babasport.pojo.Brand;
import com.babasport.service.BrandService;
import com.guwei.tools.PageHelper;
import com.guwei.tools.PageHelper.Page;

import redis.clients.jedis.Jedis;
/**
 * 品牌服务实现类 BrandServiceImpl
 * @author vian
 *
 */
@Service("brandService")
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private Jedis jedis;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Brand> findByExample(Brand brand, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		brandMapper.findByExample(brand);
		Page<Brand> endPage = PageHelper.endPage();
		return endPage;
	}


	@Override
	public Brand findById(Long id) {
		return brandMapper.findById(id);
	}


	@Override
	public void updateBrand(Brand brand) {
		brandMapper.updateById(brand);
		
		jedis.hset("brand", String.valueOf(brand.getId()), brand.getName());
	}


	@Override
	public void deleteByIds(Long[] ids) {
		brandMapper.deleteByIds(ids);
		
	}


	@Override
	public List<Brand> findAll() {
		return brandMapper.findAll();
	}


	@Override
	public List<Brand> findAllFromRedis() {
		Map<String, String> map = jedis.hgetAll("brand");
		List<Brand> brands = new ArrayList<>();
		for (Entry<String,String> en : map.entrySet()) {
			Brand brand = new Brand();
			brand.setId(Long.parseLong(en.getKey()));
			brand.setName(en.getValue());
			brands.add(brand);
		}
		return brands;
	}

}
