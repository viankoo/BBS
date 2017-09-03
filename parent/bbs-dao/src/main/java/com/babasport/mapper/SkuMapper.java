package com.babasport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.babasport.pojo.Sku;
import com.babasport.pojo.SuperPojo;
import com.github.abel533.mapper.Mapper;

/**
 * 商品明细 服务接口
 * @author vian
 *
 */
public interface SkuMapper extends Mapper<Sku>{

	/**
	 * 商品明细 保存
	 * @param sku
	 */
	void save(Sku sku);

	/**（没用superpojo的方法）
	 * 查询商品明细（关联color查询 ）
	 * @param productId
	 * @return
	 */
	List<Sku> findByPid(@Param("productId")Long productId);

	/**
	 * 根据商品id查询Sku最低价
	 * 
	 * @param id
	 * @return
	 */
	Float findMinPriceByProductId(@Param("productId")Long id);

	/**（用superpojo的方法）
	 * 查询商品明细（关联color查询 ）
	 * @param productId
	 * @return
	 */
	List<SuperPojo> findColorByProductId(@Param("pid")Long pid);

}
