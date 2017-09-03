package com.babasport.service.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babasport.service.SolrService;

/**
 * activemq 消息监听实现类
 * 
 * @author vian
 *
 */
@Service("myMessageListener")
public class MyMessageListener implements MessageListener{
	
	@Autowired
	private SolrService solrService;

	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage aMessage = (ActiveMQTextMessage) message;
		try {
			String productIds = aMessage.getText();
			System.out.println("productIds"+productIds);
			
			//执行商品添加到solr索引库的操作
			solrService.addShowProducts(productIds);
			
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("mq消费方异常!");
		}
	}

}
