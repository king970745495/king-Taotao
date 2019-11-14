package com.taotao.service;
/**
 * 商品相关处理的service
 * @author Admin
 *
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	
	//根据当前的页码，和每页的函数进行分页查询
	public EasyUIDataGridResult getItemList(Integer page,Integer rows);
	
	//新增商品的服务
	public TaotaoResult saveItem(TbItem item,String desc);
	//将新增商品的服务分两步，以方式更新索引库的时候商品条目还未插入数据库中
	public boolean saveItem_Before(long itemId,TbItem item, String desc);
	
	//商品详情展示,根据商品的id查询
	public TbItem getItemById(long itemId);
	
	//根据商品的id查询商品的描述
	public TbItemDesc getItemDescById(Long itemId);
	
	
}
