package com.babasport.service;

import java.util.List;

import com.babasport.pojo.Brand;
import com.guwei.tools.PageHelper.Page;

/**
 * 品牌服务接口 BrandService
 * 
 * @author vian
 *
 */
public interface BrandService {

	/**
	 * 分页，条件，查询，所有的品牌
	 * @param brand 
	 * @param pageSize 
	 * @param pageNum 
	 * @return
	 */
	Page<Brand> findByExample(Brand brand, Integer pageNum, Integer pageSize);

	/**
	 * 根据id查询brand
	 * @param id
	 * @return
	 */
	Brand findById(Long id);

	/**
	 * 根据id更新brand
	 * @param brand
	 */
	void updateBrand(Brand brand);

	/**
	 * 根据ids删除一个或多个品牌
	 * @param ids
	 */
	void deleteByIds(Long[] ids);

	
}
