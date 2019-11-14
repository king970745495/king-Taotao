package com.taotao.test.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.jedis.JedisClient;

public class TestJedisClient {
	/*@Test
	public void testSingle() {
		//1.初始化spring容器
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//2.获取实现类实例
		JedisClient bean=context.getBean(JedisClient.class);
		//3.调用方法操作
		bean.set("jedisclientkey", "jedisclientkey");
		System.out.println(bean.get("jedisclientkey"));
	}*/
	/*@Test
	public void testCluster() {
		//1.初始化spring容器
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//2.获取实现类实例
		JedisClient bean=context.getBean(JedisClient.class);
		//3.调用方法操作
		bean.set("jedisclientkeycluster", "jedisclientkeycluster");
		System.out.println(bean.get("jedisclientkeycluster"));
	}*/
}
