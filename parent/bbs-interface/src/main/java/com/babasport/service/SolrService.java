package com.babasport.service;

import com.babasport.pojo.SuperPojo;
import com.guwei.tools.PageHelper.Page;

/**
 * 搜索服务接口
 * 
 * @author vian
 *
 */
public interface SolrService {

	/**
	 * 根据关键字搜索商品
	 * @param keyword
	 * @param sort  字段名_排序方式
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<SuperPojo> SearchProductByKeyword(String keyword, Long brandId, Float pa, Float pb,String sort,Integer pageNum,Integer pageSize);
	
	
	/**
	 * 将上架商品信息添加到solr索引库
	 * 
	 * @param ids
	 */
	public void addShowProducts(String ids);
	
	
	
}
