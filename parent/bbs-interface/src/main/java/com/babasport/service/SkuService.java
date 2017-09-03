package com.babasport.service;

import java.util.List;

import com.babasport.pojo.Sku;

/**
 * 商品明细服务接口
 * @author vian
 *
 */
public interface SkuService {

	/**
	 * 根据商品id查询商品明细（库存）
	 * @param productId
	 * @return
	 */
	public List<Sku> findByPid(Long productId);

	/**
	 * 根据商品id查询商品明细（库存）
	 * @param productId
	 * @return
	 */
	public List<Sku> findByPid1(Long productId);

	/**
	 * 更新商品明细
	 * @param sku
	 */
	public int updateSkuById(Sku sku);
}
