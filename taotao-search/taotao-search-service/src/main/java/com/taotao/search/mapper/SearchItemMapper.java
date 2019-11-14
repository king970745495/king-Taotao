package com.taotao.search.mapper;
/**
 * 自定义的mapper.关联查询出三张表的，搜索的索引所需的数据
 * @author Admin
 *
 */

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
	//查询所有商品的索引数据
	public List<SearchItem> getSearchItemList();
	
	//根据id查询商品数据
	public SearchItem getSearchItemById(Long itemId);
	
}
