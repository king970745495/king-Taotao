package com.taotao.content.service;
/**
 * 内容处理的接口
 * @author Admin
 *
 */

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	/**
	 * 插入内容表记录
	 * @param content
	 * @return
	 */
	public TaotaoResult saveContent(TbContent content);
	/**
	 * 根据内容分类的id，查询内容分类下的列表
	 * @param categoryId
	 * @return
	 */
	public List<TbContent> getContentListByCatId(Long categoryId);
}
