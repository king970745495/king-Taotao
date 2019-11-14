package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * 用户注册的接口
 * @author Admin
 *
 */
public interface UserRegisterService {
	/**
	 * 根据参数和类型来校验数据
	 * @param param
	 * @param type  1，2，3分别代表username
	 * @return
	 */
	public TaotaoResult checkData(String param,Integer type);
	
	/**
	 * 注册用户
	 */
	public TaotaoResult register(TbUser user);
}
