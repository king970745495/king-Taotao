package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	@Autowired
	private SearchService service;
	/**
	 * 根据条件搜索商品的数据
	 * @param page
	 * @param queryString
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/search")
	public String search(@RequestParam(defaultValue = "1")Integer page,@RequestParam(value="q")String queryString,Model model) throws Exception {
		//处理get乱码，post乱码可以利用filter尽心处理，get乱码不行？
		queryString=new String(queryString.getBytes("iso-8859-1"),"utf-8");
		//1.引入，注入
		//2.调用
		SearchResult result = service.search(queryString, page, ITEM_ROWS);
		//3.设置数据传递到jsp中
		
		model.addAttribute("query",queryString);
		model.addAttribute("totalPages",result.getPageCount());//总页数
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("page", page);
		return "search";
	}
}
