package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.babasport.mapper.ProductMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestBean {

	@Autowired
	private ProductMapper productMapper;
	
	@Test
	public void test1(){
		int insert = productMapper.selectCount(null);
		System.out.println(insert);
	}
	
}
