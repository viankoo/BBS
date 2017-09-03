package com.babasport.service.message;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.servlet.ServletContext;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.guwei.tools.WebTool;

/**
 * 利用爬虫抓取商品详情页，存贮做静态化
 * 
 * @author vian
 *
 */
@Service("myMessageListener2")
public class MyMessageListener2 implements MessageListener,ServletContextAware{

	
	@SuppressWarnings("resource")
	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage amessage = (ActiveMQTextMessage) message;
		
		try {
			String productIds = amessage.getText();
			String ids = productIds.substring(1, productIds.length()-1);
			System.out.println("cms productIds:"+ids);
			String[] split = ids.split(", ");
			for (String id : split) {
				String sendGet = WebTool.sendGet("http://localhost:8082/product/detail","productId="+id, "gbk");
				String path = servletContext.getRealPath("/html/product/"+id+".html");
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
				writer.write(sendGet.toCharArray());
				writer.close();
			}
			
		} catch (JMSException | IOException e) {
			e.printStackTrace();
		}
		
	}

	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}

}
