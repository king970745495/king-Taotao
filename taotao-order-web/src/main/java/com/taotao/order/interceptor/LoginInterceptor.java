package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.sso.service.UserLoginService;
/**
 * 用户身份认证的拦截器
 * @author Admin
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private UserLoginService userLoginService;
	
	//在进入目标方法之前执行
	//做预处理相关的工作，身份认证基本在这个方法之前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		//登录的用户的身份认证
		//1.取cookie得token
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		//2.判断token是否存在于redis数据库中
		if(StringUtils.isEmpty(token)) {
			//3.如果不存在，说明没登陆
			//request.getRequestURL().toString()  访问的url   localhost:8092/order/order-cart.html
//			response.sendRedirect("http://localhost:8088/page/login？redirect="+request.getRequestURL().toString());
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL().toString());
			return false;
		}
		//4.token存在，调用sso服务
		TaotaoResult result = userLoginService.getUserByToken(token);
		if(result.getStatus()!=200) {
			//5.用户过期----》重定向到登陆界面
//			response.sendRedirect("http://localhost:8088/page/login？redirect="+request.getRequestURL().toString());
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL().toString());
			return false;
		}
		//6.用户已经登陆，放行
		//拦截得时候已经查询了用户得token对应得用户信息，拦截后得controller中又调用了一次userLoginService.getUserByToken(token);
		//因此拦截完成后，可以将该处查询得出的用户信息存到request域中
		request.setAttribute("USER-INFO", result.getData());
		return true;
	}
	
	//进入目标方法之后，在返回modelandView之前执行
	//公用变量的设置
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}
	//返回modelandView之后，渲染到页面之前执行
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}
}
