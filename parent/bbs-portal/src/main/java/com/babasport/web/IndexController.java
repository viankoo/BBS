package com.babasport.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.babasport.pojo.Brand;
import com.babasport.pojo.SuperPojo;
import com.babasport.service.BrandService;
import com.babasport.service.SolrService;
import com.guwei.tools.Encoding;
import com.guwei.tools.PageHelper.Page;

/**
 * 前台 主页控制中心
 * @author vian
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private SolrService solrService;

	@Autowired
	private BrandService brandService;
	
	//测试自动部署
	@RequestMapping("/aaa")
	public String aaaShow(){
		System.out.println("aaa");
		return "aaa";
	}
	
	//显示前台首页
	@RequestMapping("/")
	public String indexShow(){
		System.out.println("显示前台首页");
		return "index";
	}
	//显示前台首页
	@RequestMapping("/search")
	public String searchShow(Model model, String keyword, Long brandId, Float pa, Float pb, String sort, Integer pageNum, Integer pageSize){
		keyword = Encoding.encodeGetRequest(keyword);
		System.out.println("搜索 "+keyword+" ing....");
		System.out.println(keyword);
		
		Page<SuperPojo> superProducts = solrService.SearchProductByKeyword(keyword,brandId,pa,pb,sort,pageNum,pageSize);
		System.out.println("搜索出"+superProducts.getTotal()+"条记录");
		model.addAttribute("superProducts", superProducts);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("sort0", sort);
		//反转sort
		if (sort!=null&&sort.equals("price_asc")) {
			sort = "price_desc";
		} else {
			sort = "price_asc";
		}
		model.addAttribute("sort", sort);
		
		//显示品牌   brandId : brandName
		List<Brand> brands = brandService.findAllFromRedis();
		System.out.println("品牌总数："+brands.size());
		model.addAttribute("brands", brands);
		
		//回显过滤条件
		model.addAttribute("brandId", brandId);
		model.addAttribute("pa", pa);
		model.addAttribute("pb", pb);
		
		//构建回显过滤条件TreeMap
		Map<Object,Object> map = new TreeMap<>();
		if (brandId!=null) {
			for (Brand brand : brands) {
				if (brand.getId().equals(brandId)) {
					map.put("品牌", brand.getName());
				}
			}
		}
		if (pa!=null&&pb!=null) {
			if (pb.equals(-1)) {
				map.put("价格", pa+"以上");
			}else {
				map.put("价格", pa+"-"+pb);
			}
		}
		model.addAttribute("map", map);
		
		return "search";
	}
	
	
}
