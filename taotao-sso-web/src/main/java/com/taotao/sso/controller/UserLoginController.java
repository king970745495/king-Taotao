package com.taotao.sso.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.sso.service.UserLoginService;

@Controller
public class UserLoginController {
	@Autowired
	private UserLoginService loginService;
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	/**
	 * url:/user/login
	 * 参数：username  password
	 * 返回值：json
	 */
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(HttpServletRequest request,HttpServletResponse response,String username,String password) {
		
		//1.引入服务、注入服务、调用服务
		TaotaoResult result = loginService.login(username, password);
		
		//2.设置token到cookie中  可以使用工具类  cookie需要跨域？？
		if(result.getStatus()==200) {
			CookieUtils.setCookie(request, response, TT_TOKEN_KEY, result.getData().toString());
			//实现了跨域，只要是  .taotao.com结尾的域都可以接收cookie
		}
		return result;
	}
	
	/**
	 * url:/user/token/{token}
	 * 参数：token
	 * 返回值：json
	 * 请求方法：Get
	 */
	@RequestMapping(value="/user/token/{token}",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult getUserByToken(@PathVariable String token) {
		
		//1.调用服务
		TaotaoResult result = loginService.getUserByToken(token);
		//2.
		return result;
	}
}