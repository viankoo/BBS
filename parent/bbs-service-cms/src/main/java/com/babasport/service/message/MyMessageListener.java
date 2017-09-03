package com.babasport.service.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.babasport.pojo.Product;
import com.babasport.pojo.SuperPojo;
import com.babasport.service.ProductService;
import com.babasport.service.impl.StaticPageServiceImpl;

/**
 * 消息回掉处理类
 * 
 * @author vian
 *
 */
public class MyMessageListener implements MessageListener{

	@Autowired
	private StaticPageServiceImpl staticPageService;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage amessage = (ActiveMQTextMessage) message;
		
		try {
			String ids = amessage.getText();
			System.out.println("cms productIds:"+ids);
			//调用静态化，商品信息以map形式返回给页面
			for (String id : ids.substring(1, ids.length()-1).split(", ")) {
				System.out.println("cms productid:"+id);
				SuperPojo superProduct = productService.findByProductId(Long.parseLong(id));//superProduct(product:product,skus:List<superpojo(sku+color)> 
				Product product = (Product) superProduct.get("product");
				List<SuperPojo> superSkus = (List<SuperPojo>) superProduct.get("skus");
				System.out.println("product："+product);
				System.out.println("skus的数量："+superSkus.size());
				
				//利用map集合去重复，避免用在sql语句中用distinct
				Set<SuperPojo> set = new HashSet<>();
				for (SuperPojo superSku : superSkus) {
					// 定义颜色对象
					SuperPojo color = new SuperPojo();
					color.setProperty("id", superSku.get("color_id"));
					color.setProperty("name", superSku.get("name"));
					set.add(color);
				}
				superProduct.setProperty("colors", set);
				
				Map<String, Object> map = new HashMap<>();
				map.put("superProduct", superProduct);
				
				// 开始静态化
				staticPageService.staticProductPage(map,id);
			}
			
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
