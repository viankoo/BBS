package jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

/**
 * 测试jedis
 * 
 * @author vian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JedisTest {

	@Autowired
	private Jedis jedis;
	
	@Test
	public void test1(){
		jedis.set("javatest", "101");
		jedis.incr("javatest");
		System.out.println("-------------------------");
		System.out.println(jedis.get("javatest"));
		System.out.println("-------------------------");
	}
	
}
