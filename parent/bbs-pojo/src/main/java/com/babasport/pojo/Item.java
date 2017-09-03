package com.babasport.pojo;

import java.io.Serializable;

/**
 * 购物车中的商品
 * 
 * @author vian
 *
 */
public class Item implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 原始库存信息（复合型对象）
	private SuperPojo sku;

	// 库存id（单独提出来方便操作）
	private Long skuId;

	// 购买数量
	private Integer amount;

	public SuperPojo getSku() {
		return sku;
	}
	public void setSku(SuperPojo sku) {
		this.sku = sku;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
