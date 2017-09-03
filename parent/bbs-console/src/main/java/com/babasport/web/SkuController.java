package com.babasport.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babasport.pojo.Sku;
import com.babasport.service.SkuService;

/**
 * 商品明细后台管理
 * @author vian
 *
 */
@Controller
@RequestMapping("/console")
public class SkuController {

	@Autowired
	private SkuService skuService;
	
	//展示商品明细列表
	@RequestMapping("/sku/list.do")
	public String consoleSkuShowList(Long productId,Model model){
		System.out.println("展示商品明细列表");
		System.out.println(productId);
		
		List<Sku> skus = skuService.findByPid1(productId);
		model.addAttribute("skus", skus);
		System.out.println("skus的size: "+skus.size());
		
		return "/sku/list";
	}
	
	//商品信息异步更新
	@RequestMapping("/sku/update.do")
	@ResponseBody
	public String consoleSkuShowUpdate(Sku sku){
		System.out.println("商品信息异步更新");
		int i = skuService.updateSkuById(sku);
		
		return i+"";
	}
}
