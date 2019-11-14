package com.taotao.cart.service;

import java.util.List;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface CartService {
	//加入购物车
	public TaotaoResult addItemCart(TbItem item, Integer num, Long userId);
	
	public TaotaoResult updateItemCartByItemId(Long userId, Long itemId, Integer num);
	
	public TaotaoResult deleteItemCartByItemId(Long userId, Long itemId);
	/**
	 * 根据用户的ID查询用户的购物车的列表
	 * @param userId
	 * @return
	 */
	public List<TbItem> getCartList(Long userId);
	
}
