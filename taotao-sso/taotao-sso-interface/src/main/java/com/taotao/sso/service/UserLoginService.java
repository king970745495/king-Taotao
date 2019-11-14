package com.taotao.sso.service;
/**
 * 登录的接口
 * @author Admin
 *
 */

import com.taotao.common.pojo.TaotaoResult;

public interface UserLoginService {
	/**
	 * 根据用户名+密码进行登录
	 * @param username
	 * @param password
	 * @return
	 * taotaoResult  登陆成功  返回200 并且包含一个tokens数据
	 * 登陆失败，返回400
	 */
	public TaotaoResult login(String username,String password);
	
	public TaotaoResult getUserByToken(String token);
	
}
