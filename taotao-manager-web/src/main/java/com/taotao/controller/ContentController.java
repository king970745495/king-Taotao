package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 
 * 内容管理的controller
 * @author Admin
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
@Controller
public class ContentController {
	
	//注入服务层的bean
	@Autowired
	private ContentService contentService;
	
	
	//$.post("/content/save",$("#contentAddForm").serialize(), function(data){
	//返回值是json
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody//将返回的对象转化为json格式进行返回
	public TaotaoResult saveContent(TbContent tbContent) {
		//1.引入服务
		
		//2.注入服务
		
		//3.调用
		return contentService.saveContent(tbContent);
	}
}
