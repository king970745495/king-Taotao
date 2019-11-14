package com.taotao.portal.controller;
//展示首页
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;

@Controller
public class PageController {
	
	@Autowired
	private ContentService contentService;
	
	//获取属性文件中的值
	@Value("${AD1_CATEGORY_ID}")
	private long categoryId;
	
	@Value("${AD1_HEIGHT_B}")
	private String AD1_HEIGHT_B;
	
	@Value("${AD1_HEIGHT}")
	private String AD1_HEIGHT;
	
	@Value("${AD1_WIDTH}")
	private String AD1_WIDTH;
	
	@Value("${AD1_WIDTH_B}")
	private String AD1_WIDTH_B;
	
	/**
	 * 大广告位的展示、调用content层的方法，
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		
		//引入服务
		//注入服务
		//1.根据内容分类的id查询内容列表
		List<TbContent> contentList=contentService.getContentListByCatId(categoryId);
		//转成自定义的pojo列表
		List<Ad1Node> nodes=new ArrayList<Ad1Node>();
		for (TbContent tbContent : contentList) {
			Ad1Node node=new Ad1Node();
			node.setAlt(tbContent.getSubTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			nodes.add(node);
		}
		
		//传递数据给jsp
		model.addAttribute("ad1",JsonUtils.objectToJson(nodes));//将对象转换成json字符串格式
		return "index";//响应jsp
	}
}
