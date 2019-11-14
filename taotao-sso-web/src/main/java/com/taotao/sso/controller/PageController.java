package com.taotao.sso.controller;
/**
 * 跳转页面的controller
 * @author Admin
 *
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
	
	@RequestMapping("/page/{page}")
	public String showPage(@PathVariable String page,String redirect,Model model) {
		System.out.println(redirect);
		model.addAttribute("redirect", redirect);
		return page;
	}
	
	
	
	
}
