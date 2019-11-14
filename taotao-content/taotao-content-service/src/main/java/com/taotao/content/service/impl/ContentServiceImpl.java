package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import org.apache.commons.lang3.StringUtils;

@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper mapper;
	
	@Autowired  
	private JedisClient client;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Override
	public TaotaoResult saveContent(TbContent content) {
		//1.注入mapper
		
		//2.补全其他属性，插入数据
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		
		mapper.insertSelective(content);
		   
		//当添加内容的时候需要清空缓存
		try {
			client.hdel(CONTENT_KEY, content.getCategoryId()+"");
			System.out.println("当插入时，清空缓存");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
	
	/**
	 * 大广告位的图片展示，添加缓存，因为首页的访问量、并发量比较高，所以添加缓存可以提高系统速度
	 */
	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		/**添加缓存不能影响正常的业务逻辑
		*/
		//判断redis中是否有数据，如果有就直接从redis中返回数据
		try {
			String jsonstr = client.hget(CONTENT_KEY, categoryId+"");//从数据库中获取内容分类下的所有的内容数据
			//如果存在，说明有缓存
			if(StringUtils.isNotBlank(jsonstr)) {
				System.out.println("有缓存");
				return JsonUtils.jsonToList(jsonstr, TbContent.class);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//1.注入Mapper
		//2.创建example
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//3.设置查询条件
		
		//4.执行查询
		List<TbContent> list = mapper.selectByExample(example);
		
		//将数据写入到redis数据库中
		
		//注入jedisClient
		
		//set写入数据？？思考写入数据的类型是什么？用hash
		try {
			System.out.println("没有缓存");
			client.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}//服务已经发布，因此接下来开发controller
	
	
	
	
}
