package com.babasport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.babasport.mapper.ProductMapper;
import com.babasport.mapper.SkuMapper;
import com.babasport.pojo.Product;
import com.babasport.pojo.SuperPojo;
import com.babasport.service.SolrService;
import com.github.abel533.entity.Example;
import com.guwei.tools.PageHelper.Page;

/**
 * 搜索服务实现类
 * 
 * @author vian
 *
 */
@Service("solrService")
@Transactional
public class SolrServiceImpl implements SolrService{
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public Page<SuperPojo> SearchProductByKeyword(String keyword, Long brandId, Float pa, Float pb,String sort,Integer pageNum,Integer pageSize) {
		
		try {
			// 设置查询条件
			SolrQuery solrQuery = new SolrQuery("name_ik:" + keyword);
			
			//设置过滤条件品牌
			if (brandId!=null) {
				solrQuery.addFilterQuery("brandId:"+brandId);
			}
			
			//设置过滤条件价格
			if (pa!=null&&pb!=null) {
				if (pb==-1) {
					solrQuery.addFilterQuery("price:["+pa+" TO *]");
				}else{
					solrQuery.addFilterQuery("price:["+pa+" TO "+pb+"]");
				}
			}
			
			//设置分页查询条件,利用pageHelper中的Page对象初始化pageNum和pageSize
			Page<SuperPojo> page = new Page<>(pageNum, pageSize);
			solrQuery.setStart(page.getStartRow());
			solrQuery.setRows(page.getPageSize());
			
			//设置高亮
			solrQuery.setHighlight(true);
			solrQuery.addHighlightField("name_ik");
			solrQuery.setHighlightSimplePre("<span style='color:red'>");
			solrQuery.setHighlightSimplePost("</span>");
			
			//排序
			if (sort!=null&&sort.trim().length()>0) {
				SortClause sortClause = new SortClause(sort.split("_")[0], sort.split("_")[1]);
				solrQuery.setSort(sortClause);
			}
			
			// 开始查询
			QueryResponse response = solrServer.query(solrQuery);
			
			//获取高亮数据集合  <docs,<name_ik,.....>>
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			
			//获取查询结果  的 DocumentList
			SolrDocumentList docs = response.getResults();
			
			//获取查询总条数
			long numFound = docs.getNumFound();
			page.setTotal(numFound);
			System.out.println("共查询出"+numFound+"条结果");
			
			//查询出Document对象结果集,将结果集封装到List<SuperPojo>
			List<SuperPojo> superProducts = new ArrayList<>();
			for (SolrDocument doc : docs) {
				SuperPojo pojo = new SuperPojo();
				pojo.setProperty("id", doc.getFieldValue("id"));
				//pojo.setProperty("name", doc.getFieldValue("name_ik"));
				Map<String, List<String>> d = highlighting.get(doc.getFieldValue("id"));
				pojo.setProperty("name", d.get("name_ik").get(0));
				pojo.setProperty("url", doc.getFieldValue("url"));
				pojo.setProperty("brandId", doc.getFieldValue("brandId"));
				pojo.setProperty("price", doc.getFieldValue("price"));
				superProducts.add(pojo);
			}
			
			System.out.println("结果集封装完成,一共查出"+superProducts.size()+"条记录");
			page.setResult(superProducts);
			return page;
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addShowProducts(String ids) {
		try {
			List<Object> list = new ArrayList<>();
			String[] split = ids.substring(1, ids.length()-1).split(", ");
			for (String id : split) {
				list.add(id);
			}
			Example example = new Example(Product.class);
			example.createCriteria().andIn("id", list);
			List<Product> products = productMapper.selectByExample(example);
			for (Product product : products) {
				//将商品信息保存到SolrInputDocument
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", Long.valueOf(product.getId()));
				doc.addField("name_ik", product.getName());
				doc.addField("url", product.getImgUrl().split(",")[0]);
				doc.addField("brandId", product.getBrandId());
				//skuPrice是该商品旗下库存的最低价
				Float price = skuMapper.findMinPriceByProductId(product.getId());
				doc.addField("price", price);
				System.out.println("SolrInputDocument:id="+doc.get("id")+",name="+doc.get("name_ik")+",price="+doc.get("brandId"));
				solrServer.add(doc);
				solrServer.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("商品上架出错");
		}
		
	}


	
	
	
}
