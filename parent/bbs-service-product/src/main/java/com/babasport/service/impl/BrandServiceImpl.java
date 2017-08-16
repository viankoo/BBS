package com.babasport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babasport.mapper.BrandMapper;
import com.babasport.pojo.Brand;
import com.babasport.service.BrandService;
import com.guwei.tools.PageHelper;
import com.guwei.tools.PageHelper.Page;
/**
 * 品牌服务实现类 BrandServiceImpl
 * @author vian
 *
 */
@Service("brandService")
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandMapper brandMapper;
	

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
	}


	@Override
	public void deleteByIds(Long[] ids) {
		brandMapper.deleteByIds(ids);
	}

}
