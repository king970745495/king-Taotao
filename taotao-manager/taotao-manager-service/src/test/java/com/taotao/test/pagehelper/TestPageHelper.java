package com.taotao.test.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {
	@Test
	public void testhelper() {
		
		//2.初始化spring容器
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		//3.获取mapper的代理对象
		TbItemMapper itemMapper=applicationContext.getBean(TbItemMapper.class);
		
		//1.设置分页信息，只有紧跟着的第一个select会被分页
		PageHelper.startPage(1, 5);
		
		//4.调用mapper的方法查询数据
		TbItemExample example=new TbItemExample();
		List<TbItem> list=itemMapper.selectByExample(example);//查询条件为空，相当于select * 
		
		List<TbItem> list2=itemMapper.selectByExample(example);//上一个会被分页，这一个不会被分页
		
		//5.遍历结果集  打印
		//获取分页信息打印
		PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(list);
		System.out.println(pageInfo.getPageSize());
		System.out.println("list2的总条数"+list2.size());
		
		for (TbItem tbItem : list) {
			System.out.println(tbItem);
		}
	}
}
