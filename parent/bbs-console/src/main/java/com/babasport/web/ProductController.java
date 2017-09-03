package com.babasport.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.babasport.pojo.Brand;
import com.babasport.pojo.Color;
import com.babasport.pojo.Product;
import com.babasport.service.BrandService;
import com.babasport.service.ProductService;
import com.guwei.tools.Encoding;
import com.guwei.tools.PageHelper.Page;

/**
 * 商品后台管理
 * @author vian
 *
 */
@Controller
@RequestMapping("/console")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/product/{pageName}.do")
	public String consoleProductShow(@PathVariable("pageName")String pageName){
		System.out.println("通用product转发："+pageName);
		return "/product/"+pageName;
	}
	
	//展示商品列表
	@RequestMapping("/product/list.do")
	public String consoleProductShowList(Integer pageNum,Integer pageSize,Product product,Model model){
		System.out.println("展示商品列表");
		
		String name = Encoding.encodeGetRequest(product.getName());
		product.setName(name);
		System.out.println(product.getName()+"...........其他条件");
		System.out.println(pageNum+"..............."+pageSize);
		Page<Product> products = productService.findByExample(product,pageNum,pageSize);
		System.out.println("products:"+products.getTotal());
		
		model.addAttribute("pageproducts", products);
		model.addAttribute("name", name);
		
		return "/product/list";
	}
	
	//展示商品添加页面
	@RequestMapping("/product/toAdd.do")
	public String consoleProductShowToAdd(Model model){
		System.out.println("展示商品添加页面");
		
		List<Color> colors = productService.findAllColor();
		model.addAttribute("colors", colors);
		
		List<Brand> brands = brandService.findAll();
		model.addAttribute("brands", brands);
		
		return "/product/add";
	}
	
	//商品添加
	@RequestMapping("/product/add.do")
	public String consoleProductShowAdd(Product product){
		System.out.println("商品添加");
		
		productService.addProduct(product);
		return "redirect:/console/product/list.do";
	}
	
	//上架
	@RequestMapping("/product/isShow.do")
	public String consoleProductShowisShow(Long[] ids){
		System.out.println("商品上架");
		
		productService.updateIsShow(ids);
		return "redirect:/console/product/list.do";
	}
	//下架
	@RequestMapping("/product/isHide.do")
	public String consoleProductShowisHide(Long[] ids){
		System.out.println("商品下架");
		productService.updateIsHide(ids);
		return "redirect:/console/product/list.do";
	}
	
}
