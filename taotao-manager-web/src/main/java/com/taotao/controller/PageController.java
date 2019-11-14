package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示页面
 * @author Admin
 *
 */
@Controller
public class PageController {
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	//显示商品查询的页面
	//url:item-list,restful风格
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
	//与下面的等价，下面的参数名称与表达式名称不一致
//	@RequestMapping("/{page}")
//	public String showPage(@PathVariable(value="page") String page333) {
//		return page333;
//	}

}
