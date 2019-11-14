package com.taotao.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Controller
public class HtmlGen {
	
	@Autowired
	private FreeMarkerConfigurer config;
	
	@RequestMapping("/genHtml")
	@ResponseBody
	public String gen() throws Exception{
		//生成静态页面
		
		//1.根据config  获取configuration对象
		Configuration configuration=config.getConfiguration();
		//2.设置模板文件  加载模板文件  相对路径,相对于/WEB-INF/ftl/
		Template template = configuration.getTemplate("template.ftl");
		//3.创建数据集--》从数据库中获取
		Map model=new HashMap<>();
		model.put("springtestkey", "hello");
		//4.创建writer
		Writer writer=new FileWriter(new File("D:/【学习资料】/【0】传智播客2018_JavaEE/freemarker/springtestkey.html"));
		//5.调用方法输出
		template.process(model, writer);
		//6.关闭流
		writer.close();
		return "ok";
	}
	
	
}
