package com.babasport.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理  控制中心
 * @author vian
 *
 */
@Controller
@RequestMapping("/console")
public class CenterController {
	
	
	//主页面
	@RequestMapping(value="/{pageName}.do")
	public String consoleShow(Model model,@PathVariable("pageName")String pageName){
		System.out.println("consoleShow:"+pageName);
		return pageName;
	}
	
	//框架页面
	@RequestMapping(value="/frame/{pageName}.do")
	public String consoleFrameShow(Model model,@PathVariable("pageName")String pageName){
		System.out.println("consoleFrameShow"+pageName);
		
		
		
		return "frame/"+pageName;
	}
	
	
}
