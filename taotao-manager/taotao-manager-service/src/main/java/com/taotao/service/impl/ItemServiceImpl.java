package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper mapper;
	
	@Autowired
	private TbItemDescMapper descmapper;
	
	@Autowired
	private JmsTemplate jmstemplate;
	@Autowired
	@Qualifier("topicDestination")
	private Destination destination;
	
	
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//1.使用pagehelper设置分页信息
		if(page==null)page=1;
		if(rows==null)rows=30;
		PageHelper.startPage(page, rows);
		
		//2.注入mapper
		//3.创建example，对象，进行查询
		TbItemExample example=new TbItemExample();
		List<TbItem> list=mapper.selectByExample(example);
		
		//4.获取分页的信息
		PageInfo<TbItem> info=new PageInfo<TbItem>(list);
		
		//5.封装到EasyUIDataGridResult对象
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setTotal((int)info.getTotal());
		result.setRows(info.getList());
		//6.返回
		return result;
	}
	
	
	public boolean saveItem_Before(long itemId,TbItem item, String desc) {
		
		//1.补全item 的其他属性
		item.setId(itemId);
		item.setCreated(new Date());
		//1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setUpdated(item.getCreated());
		//2.插入到item表 商品的基本信息表
		mapper.insertSelective(item);
		//3.补全商品描述中的属性
		TbItemDesc desc2 = new TbItemDesc();
		desc2.setItemDesc(desc);
		desc2.setItemId(itemId);
		desc2.setCreated(item.getCreated());
		desc2.setUpdated(item.getCreated());
		//4.插入商品描述数据
			//注入tbitemdesc的mapper
		int i = descmapper.insertSelective(desc2);
		//5.返回taotaoresult
		if(i!=0) {
			return true;
		}else {
			return false;
		}	
	}
	
	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		
		//生成商品的id
		final long itemId= IDUtils.genItemId();
		
		boolean result =this.saveItem_Before(itemId,item, desc);
		if(result) {
			
			//添加发送消息的业务逻辑
			jmstemplate.send(destination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(itemId+"");
				}
			});
		}
		// 5.返回taotaoresult
		return TaotaoResult.ok();
	}

	
	
	/**
	 * 以下两个方法需要添加缓存
	 * 注意：添加缓存不能打乱原来的程序逻辑
	 */
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	@Override
	public TbItem getItemById(long itemId) {
		//添加缓存
		
		//1.从缓存中获取数据，如果有直接返回
		try {
			String jsonStr = jedisClient.get(ITEM_INFO_KEY+":"+itemId+":BASE");
			
			//设置有效期，以使热点的数据一直存在与缓存中
			if(StringUtils.isNotBlank(jsonStr)) {
				jedisClient.expire(ITEM_INFO_KEY+":"+itemId+":BASE", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonStr, TbItem.class);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//2.如果没有数据
		//注入mapper
		//调用方法
		TbItem tbItem=mapper.selectByPrimaryKey(itemId);
		//返回tabitem
		
		//3.添加缓存到redis数据库中
			//注入jedisclient
//		ITEM_INFO:123456:BASE
//		ITEM_INFO:123456:DESC
		try {
			jedisClient.set(ITEM_INFO_KEY+":"+itemId+":BASE", JsonUtils.objectToJson(tbItem));	
			//设置缓存有效期
			jedisClient.expire(ITEM_INFO_KEY+":"+itemId+":BASE", ITEM_INFO_KEY_EXPIRE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tbItem;
	}


	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		
		//添加缓存
		
		//1.从缓存中获取数据，如果有直接返回
		try {
			String jsonStr = jedisClient.get(ITEM_INFO_KEY+":"+itemId+":DESC");
			
			//设置有效期，以使热点的数据一直存在与缓存中
			if(StringUtils.isNotBlank(jsonStr)) {
				System.out.println("有缓存");
				jedisClient.expire(ITEM_INFO_KEY+":"+itemId+":DESC", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonStr, TbItemDesc.class);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//缓存中没有数据
		TbItemDesc tbItemDesc=descmapper.selectByPrimaryKey(itemId);
		
		//3.添加缓存到redis数据库中
			//注入jedisclient
	//	ITEM_INFO:123456:BASE
	//	ITEM_INFO:123456:DESC
		try {
			jedisClient.set(ITEM_INFO_KEY+":"+itemId+":DESC", JsonUtils.objectToJson(tbItemDesc));	
			//设置缓存有效期
			jedisClient.expire(ITEM_INFO_KEY+":"+itemId+":DESC", ITEM_INFO_KEY_EXPIRE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tbItemDesc;
	}
}
