package solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试solrj
 * 
 * @author vian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestSolrj {

	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 创建索引（使用spring）
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Test
	public void test1() throws SolrServerException, IOException{
		//使用solr输入文档（SolrInputDocument）创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		//添加字段到文档对象
		document.addField("id", "1");
		document.addField("test_ik", "顾伟测试,创建索引");
		//添加文档到solr服务器对象
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
}
