package com.taotao.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;

@Controller
public class OrderController {

	private String TT_CART_KEY;

	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;

//	@Autowired
//	private OrderService orderservice;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserLoginService userLoginService;

	/**
	 * 展示购物车中的商品的列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request,HttpServletResponse response){
		System.out.println(request.getAttribute("USER-INFO"));
		//1.通过cookie获取token,调用SSO服务获取用户信息
//		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
//		TbUser user = null;
//		if(StringUtils.isNotBlank(token)){
//			TaotaoResult result = userLoginService.getUserByToken(token);
//			if(result.getStatus()==200){
//				user = (TbUser)result.getData();
//				List<TbItem> cartList = cartService.getCartList(user.getId());
//				request.setAttribute("cartList", cartList);
//			}
//		}
		TbUser user = (TbUser)request.getAttribute("USER-INFO");
		List<TbItem> cartList = cartService.getCartList(user.getId());
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
}
