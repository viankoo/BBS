package com.babasport.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.babasport.pojo.TestTb;
import com.babasport.service.TestTbService;

/**
 * 商品后台管理
 * @author vian
 *
 */
@Controller
@RequestMapping("/console")
public class ProductController {

	@RequestMapping("/product/{pageName}.do")
	public String consoleProductShow(@PathVariable("pageName")String pageName){
		System.out.println("consoleProductShow"+pageName);
		return "/product/"+pageName;
	}
	
}
