package com.babasport.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.babasport.pojo.Product;
import com.babasport.pojo.SuperPojo;
import com.babasport.service.ProductService;

/**
 * 商品详情 控制中心
 * 
 * @author vian
 *
 */
@SuppressWarnings("unchecked")
@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;

	//单个商品详情
	@RequestMapping("/product/detail")
	public String showSingleProduct(Model model,Long productId){
		System.out.println("单个商品详情");
		System.out.println("productId:"+productId);
		
		SuperPojo superProduct = productService.findByProductId(productId);//superProduct(product:product,skus:List<superpojo(sku+color)> 
		Product product = (Product) superProduct.get("product");
		List<SuperPojo> superSkus = (List<SuperPojo>) superProduct.get("skus");
		System.out.println("product："+product);
		System.out.println("skus的数量："+superSkus.size());
		
		//利用map集合去重复，避免用在sql语句中用distinct
		HashMap<Object,Object> color = new HashMap<>();
		for (SuperPojo superSku : superSkus) {
			color.put(superSku.get("color_id"), superSku.get("name"));
		}
		model.addAttribute("map", color);
		
		model.addAttribute("superProduct", superProduct);
		return "product";
	}
}
