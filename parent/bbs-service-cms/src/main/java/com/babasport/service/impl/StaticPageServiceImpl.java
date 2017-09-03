package com.babasport.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.activemq.ActiveMQConnectionConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.babasport.service.StaticPageService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 静态化页面实现类
 * 
 * @author vian
 *
 */
@Service("staticPageService")
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	public void staticProductPage(Map<String, Object> map, String id) {
		try {
			// 模版 + 数据模型 = 数据输出
			//使用spring的Freemarker配置获得标准的Freemark配置器
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
		
			//加载模板文件
			Template template = configuration.getTemplate("product.html");
			
			//设置输出文件的位置
			String path = servletContext.getRealPath("/html/product/"+id+".html");
			File file = new File(path);
			//检查父目录是否存在
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdir();
			}
			
			Writer out = new FileWriter(new File(path));
			//进行处理
			template.process(map, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
	}

	
	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

}
