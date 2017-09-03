package com.babasport.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.babasport.mapper.ColorMapper;
import com.babasport.mapper.ProductMapper;
import com.babasport.mapper.SkuMapper;
import com.babasport.pojo.Color;
import com.babasport.pojo.Product;
import com.babasport.pojo.Sku;
import com.babasport.pojo.SuperPojo;
import com.babasport.service.ProductService;
import com.github.abel533.entity.Example;
import com.guwei.tools.PageHelper;
import com.guwei.tools.PageHelper.Page;

import redis.clients.jedis.Jedis;

/**
 * 商品服务实现类
 * @author vian
 *
 */
@Service("productService")
@Transactional
public class ProductServieImpl implements ProductService{

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ColorMapper colorMapper;
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private Jedis jedis;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public Page<Product> findByExample(Product product, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		Example example = new Example(Product.class);
		if (product.getName()==null) {
			product.setName("");
		}
		example.createCriteria().andLike("name", "%"+product.getName()+"%");
		example.setOrderByClause("createTime desc");
		productMapper.selectByExample(example);
		
		Page<Product> endPage = PageHelper.endPage();
		return endPage;
	}

	@Override
	public List<Color> findAllColor() {
		Example example = new Example(Color.class);
		example.createCriteria().andNotEqualTo("parentId", 0);
		List<Color> colors = colorMapper.selectByExample(example);
		return colors;
	}

	@Override
	public void addProduct(Product product) {
		product.setId(jedis.incr("productId"));;
		productMapper.insert(product);
		System.out.println("新增商品的id："+product.getId());
		System.out.println(product);
		
		String[] colors = product.getColors().split(",");
		String[] sizes = product.getSizes().split(",");
		
		for (String color : colors) {
			for (String size : sizes) {
				
				Sku sku = new Sku();
				sku.setProductId(product.getId());
				sku.setColorId(Long.parseLong(color));
				sku.setSize(size);
				sku.setMarketPrice(1000.00f);
				sku.setPrice(800.00f);
				sku.setDeliveFee(20f);
				sku.setStock(0);
				sku.setUpperLimit(100);
				sku.setCreateTime(new Date());
				
				skuMapper.insert(sku);
			}
		}
	}

	/**
	 * 商品上架时   利用MQ消息队列,把要上架的商品id放入队列
	 * 要将该商品的关键明细保存到solr中，以便前台查询，展示
	 * productName productImgurl skuPrice(最低价) productId  品牌id（高亮所需）
	 */
	@Override
	public void updateIsShow(final Long[] ids) {
		//商品上架
		for (Long id : ids) {
			Product product = productMapper.selectByPrimaryKey(id);
			product.setIsShow(1);
			productMapper.updateByPrimaryKeySelective(product);
		}
		
		//将要上架商品id，发送给mq消息队列(订阅模式),异步录入solr索引库,静态化
		jmsTemplate.send("productIds", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(Arrays.toString(ids));
			}
		});
		
	}

	@Override
	public void updateIsHide(Long[] ids) {
		for (Long id : ids) {
			Product product = productMapper.selectByPrimaryKey(id);
			product.setIsShow(0);
			productMapper.updateByPrimaryKeySelective(product);
		}
		
	}

	@Override
	public SuperPojo findByProductId(Long productId) {
		//查询出简单商品信息
		Product product= productMapper.selectByPrimaryKey(productId);
		//在查询出商品的库存sku
		//查询出库存信息并把颜色查出来一起封装到superpojo
		List<SuperPojo> skus = skuMapper.findColorByProductId(productId);
		
		
		//将商品详情所需信息全部封装到superpojo
		SuperPojo superProduct = new SuperPojo();
		superProduct.setProperty("product", product);
		superProduct.setProperty("skus", skus);
		
		return superProduct;
	}
}
