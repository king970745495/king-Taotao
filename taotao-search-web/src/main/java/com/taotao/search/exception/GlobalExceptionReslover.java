package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * 全局异常处理器的类
 * @author Admin
 *
 */
public class GlobalExceptionReslover implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//1.日志写入到日志文件中，这里打印
		System.out.println(ex.getMessage());
		ex.printStackTrace();
		//2.及时通知开发人员(通过第三方接口进行发送)
		System.out.println("发短信");
		
		//3.给用户一个有好的提示：您的网络有异常
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("error/exception");
		modelAndView.addObject("message","您的网络有异常，请重试");
		return modelAndView;
	}

}
