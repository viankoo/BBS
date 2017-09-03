package com.babasport.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 购物车对象类，里面包括用户选择的多个库存商品，以及结算等相关信息
 * @author vian
 *
 */
public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Item> items = new ArrayList<>();

	
	/**
	 * 自定义添加item到cart中方法，如果item的skuId（id）一样，则item的购买数量加上amount
	 * 
	 * @param item
	 */
	public void addItem(Item item){
		for (Item item2 : items) {
			if (item.getSkuId().equals(item2.getSkuId())) {
				item2.setAmount(item2.getAmount() + item.getAmount());
				return; 
			}
		}
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}
