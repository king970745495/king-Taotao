package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface UserExitService {
	
	/**
	 * 安全退出
	 */
	public TaotaoResult deleteUserByToken(String token);
	
	
}
