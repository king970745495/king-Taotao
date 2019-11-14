package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.TestService;

/**
 * 测试使用的
 * 
 * @author Admin
 *
 */
@Controller
public class TestController {
	
	@Autowired
	private TestService testservice;
	
	@RequestMapping("test/queryNow")
	@ResponseBody
	public String queryNow(){
		return testservice.queryNow();
	}
}
