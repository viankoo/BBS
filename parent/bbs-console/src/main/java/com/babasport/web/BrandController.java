package com.babasport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babasport.pojo.Brand;
import com.babasport.service.BrandService;
import com.guwei.tools.Encoding;
import com.guwei.tools.PageHelper.Page;

/**
 * 品牌后台管理  
 * @author vian
 *
 */
@Controller
@RequestMapping("/console")
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/brand/{pageName}.do")
	public String consoleBrandShow(@PathVariable("pageName")String pageName){
		System.out.println("通用consoleBrandShow"+pageName);
		return "/brand/"+pageName;
	}
	
	@RequestMapping("/brand/list.do")
	public String consoleBrandShowList(Integer pageNum,Integer pageSize,Brand brand,Model model){
		System.out.println("consoleBrandShowList:list");
		
		String name = Encoding.encodeGetRequest(brand.getName());
		brand.setName(name);
		System.out.println(brand.getName()+"..........."+brand.getIsDisplay());
		System.out.println(pageNum+"..............."+pageSize);
		Page<Brand> brands = brandService.findByExample(brand,pageNum,pageSize);
		System.out.println("brands:"+brands.getTotal());
		
		model.addAttribute("pagebrands", brands);
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", brand.getIsDisplay());
		
		return "/brand/list";
	}
	//品牌编辑页面
	@RequestMapping("/brand/toEdit.do")
	public String consoleBrandToEditShow(Model model,@RequestParam("id")Long id){
		System.out.println("consoleBrandToEditShow:"+id);
		
		Brand brand = brandService.findById(id);
		System.out.println(brand);
		model.addAttribute("brand", brand);
		
		return "/brand/edit";
	}
	//品牌编辑
	@RequestMapping("/brand/edit.do")
	public String consoleBrandEditShow(Brand brand){
		System.out.println("consoleBrandEditShow:"+brand.toString());
		brandService.updateBrand(brand);
		return "redirect:/console/brand/list.do";
	}
	//品牌批量删除
	@RequestMapping("/brand/delete.do")
	public String consoleBrandDeleteShow(Long[] ids,String name,String isDisplay,String pageNum){
		System.out.println("consoleBrandDeleteShow:"+name+"...|"+isDisplay+"....|"+pageNum+"...|"+ids);
		brandService.deleteByIds(ids);
		return "redirect:/console/brand/list.do?name=" + name + "&isDisplay="
				+ isDisplay + "&pageNum=" + pageNum;
	}
	
	
	@RequestMapping("brand/findName.do")
	@ResponseBody
	public List<Brand> ajaxGetBrandName(){
		List<Brand> brands = brandService.findAll();
		return brands;
	}
}
