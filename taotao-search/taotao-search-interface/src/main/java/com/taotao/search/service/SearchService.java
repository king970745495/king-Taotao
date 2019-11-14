package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;

public interface SearchService {
	//导入所有的商品数据到索引库中
	public TaotaoResult importSearchitems () throws Exception;
	
	/**
	 * @param queryString  查询的主条件
	 * @param page  查询当前显示的页码
	 * @param rows  每页显示的行数，在controller中写死
	 * @return
	 * @throws Exception
	 */
	//根据搜索的条件搜索的结果
	public SearchResult search(String queryString,Integer page,Integer rows) throws Exception;
	
	//根据传递过来消息的id更新索引库
	public TaotaoResult updateSearchItemById(long itemId) throws Exception;
	
}
