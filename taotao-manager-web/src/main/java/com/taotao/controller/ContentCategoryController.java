package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.pojo.TbContent;

/**
 * 内容分类处理controller
 * @author Admin
 *
 */

@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService service;
	/**
	 * url : '/content/category/list',
		animate: true,
		method : "GET",
		参数:id
	 */
	@RequestMapping(value="/content/category/list",method=RequestMethod.GET)
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue = "0") long parentId){
		//1.引入服务
		//在springmvc中引入，自动注入该服务
		
		//2.调用服务
		//3.返回
		return service.getContentCategoryList(parentId);
	}
	
	//url:'/content/query/list',queryParams:{categoryId:0}
	@RequestMapping(value="/content/query/list",method=RequestMethod.GET)
	@ResponseBody
	public List<TbContent> getContentQueryList(@RequestParam(value="categoryId",defaultValue = "0") long categoryId){
		
		//1.引入服务
		//在springmvc中引入，自动注入该服务
		
		//2.调用服务
		//3.返回
		return service.getContentQueryList(categoryId);
	}
	
	///content/category/create
	//method=post
	//参数：{parentId:node.parentId,name:node.text}
	//name:新增节点的文本
	//返回值taotaoresult 包含分类的id
	/**
	 * 添加节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createContentbCategory(Long parentId,String name) {
		//1.
		return service.createContentCategory(parentId, name);
	}
	
	//$.post("/content/category/update",{id:node.id,name:node.text});
	@RequestMapping(value="/content/category/update",method=RequestMethod.POST)
	@ResponseBody
	public void updateContentCategory(Long id,String name) {
		//1.
		service.updateContentCategory(id, name);
	}
	
	//$.post("/content/category/delete",{id:node.id},function(){
	@RequestMapping(value="/content/category/delete",method=RequestMethod.POST)
	@ResponseBody
	public boolean deleteContentCategory(Long id) {
		//1.
		return service.deleteContentCategory(id);
	}
	
	
}
