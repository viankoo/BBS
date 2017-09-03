package com.babasport.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babasport.pojo.Buyer;
import com.babasport.service.BuyerService;
import com.babasport.service.SessionService;
import com.guwei.tools.Encryption;
import com.guwei.tools.SessionTool;

/**
 * 登录控制中心
 * @author vian
 *
 */
@Controller
public class LoginController {

	@Autowired
	private BuyerService buyerService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="/login.aspx",method=RequestMethod.GET)
	public String toLogin(){
		System.out.println("显示登录页面");

		return "login";
	}
	
	@RequestMapping(value="/login.aspx",method=RequestMethod.POST)
	public String login(String returnUrl,String username,String password,Model model,HttpServletRequest request,HttpServletResponse response){
		System.out.println("用户尝试登录");
		String error = "";
		if (username!=null) {
			if (password!=null) {
				Buyer buyer = buyerService.findByUsername(username);
				System.out.println(buyer);
				if (buyer!=null) {
					if (buyer.getPassword().equals(Encryption.encrypt(password))) {
						System.out.println("登陆成功");
						
						// 将用户名保存到自定义session中(redis)
						sessionService.addUsernameToRedis(
								SessionTool.getSessionID(request, response),
								username);
						
						// 如果用户直接打开登录页面，则登录后返回首页
						if (returnUrl == null || returnUrl.length() < 1) {
							returnUrl = "http://localhost:8082/";
						}
						return "redirect:"+returnUrl;
					}
				} else {
					//密码错误
					error="密码错误";
				}
			} else {
				//密码不能为空
				error="密码不能为空";
			}
		} else {
			//用户名 不能为空
			error="用户名 不能为空";
		}
		model.addAttribute("error", error);
		return "login";
	}
	
	//验证是否登录
	@RequestMapping("/isLogin.aspx")
	@ResponseBody
	public MappingJacksonValue  isLogin(String callback,HttpServletRequest request,HttpServletResponse response){
		System.out.println("callback:"+callback);
		
		String username = sessionService
				.getUsernameFromRedis(SessionTool.getSessionID(request, response));
		
		System.out.println(SessionTool.getSessionID(request, response));
		System.out.println(username);
		
		MappingJacksonValue value = new MappingJacksonValue(username);
		value.setJsonpFunction(callback);
		
		return value;
	}
	
	
	
	
}
