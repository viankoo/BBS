package com.babasport.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.babasport.pojo.Cart;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 购物车控制中心
 * @author vian
 *
 */
@Controller
public class CartController {

	//显示购物车页面
	@RequestMapping("/cart")
	public String showCart(Model model,HttpServletRequest request,HttpServletResponse response){
		System.out.println("显示购物车页面");
		//从cookie中取出cart对象
		Cart cart = this.getCartFromCookie(request);
		System.out.println(cart);
		return "cart";
	}

	//从cookie中取出cart对象
	public Cart getCartFromCookie(HttpServletRequest request) {
		Cart cart = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("cart".equals(cookie.getName())) {
				String value = cookie.getValue();
				// 将购物车的json字符串转成购物车对象
				ObjectMapper om = new ObjectMapper();
				try {
					cart = om.readValue(value, Cart.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			} 
		}
		return cart;
	}
	
	//添加商品到购物车中
	public String addCart(Long skuid,Model model,HttpServletRequest request,HttpServletResponse response){
		System.out.println("添加商品到购物车中");
		
		
		return "redirect:/cart";
	}
	
}
