package com.babasport.service;

import java.util.List;

import com.babasport.pojo.Brand;
import com.babasport.pojo.Color;
import com.babasport.pojo.Product;
import com.babasport.pojo.SuperPojo;
import com.guwei.tools.PageHelper.Page;

/**
 * 商品服务接口
 * @author vian
 *
 */
public interface ProductService {

	/**
	 * 分页条件查询商品
	 * @param product
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Page<Product> findByExample(Product product, Integer pageNum, Integer pageSize);

	/**
	 * 查询所有颜色
	 * @return
	 */
	List<Color> findAllColor();

	/**
	 * 添加商品
	 * @param product
	 */
	void addProduct(Product product);

	/**
	 * 商品上架
	 * @param ids
	 */
	void updateIsShow(Long[] ids);

	/**
	 * 商品下架
	 * @param ids
	 */
	void updateIsHide(Long[] ids);

	/**
	 * 根据商品id查询商品的信息
	 * 	商品信息 + （库存信息+颜色信息）
	 * @param productId
	 * @return
	 */
	SuperPojo findByProductId(Long productId);


}
