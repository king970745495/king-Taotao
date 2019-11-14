package com.taotao.test.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	//测试单机版jedis
	//不使用jedis连接池
	/*@Test
	public void testJedis() {
		//1.创建Jedis对象，需要指定连接的地址和端口
		Jedis jedis=new Jedis("192.168.25.128",6379);
		//2.直接操作redis  set
		jedis.set("name","king");
		System.out.println(jedis.get("name"));
		//3.关闭jedis
		jedis.close();
		
	}*/
	//使用jedis连接池
	/**
	 * 1.得到连接池对象（指定ip和端口）
	 * 2.获取jedis对象
	 * 3.使用jedis对象
	 * 4.jedis对象使用完后，释放jedis资源
	 * 5.最后应用关闭的时候，才关闭整个连接池
	 */
	/*@Test
	public void testJedisPool() {
		
		JedisPool pool=new JedisPool("192.168.25.128",6379);
		Jedis jedis=pool.getResource();
		jedis.set("king", "liujin");
		System.out.println(jedis.get("king"));
		jedis.close();
		pool.close();
		
	}*/
	
	//测试集群版jedis
	@Test
	public void testJedisCluster() {
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		//1.创建jediscluster对象
		JedisCluster cluster=new JedisCluster(nodes);
		//2.直接根据jediscluster对象操作redis集群
		cluster.set("cluster","cluster");
		System.out.println(cluster.get("cluster"));
		//3.关闭Jediscluster对象
		cluster.close();
	}
	
	
	
	
	
	
	
	
	
	
}
