package com.babasport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.babasport.pojo.Brand;

public interface BrandMapper {

	/**
	 * 条件查询所有品牌
	 * @param brand
	 * @param pageSize 
	 * @param pageNum 
	 * @return
	 */
	List<Brand> findByExample(Brand brand);

	/**
	 * 根据id查询品牌brand
	 * @param id
	 * @return
	 */
	Brand findById(Long id);

	/**
	 * 根据id更新品牌brand
	 * @param brand
	 */
	void updateById(Brand brand);

	/**根据ids逻辑删除多个品牌
	 * @param ids
	 */
	void deleteByIds(@Param("ids")Long[] ids);

	/**
	 * 查询所有的品牌
	 * @return
	 */
	List<Brand> findAll();

}
