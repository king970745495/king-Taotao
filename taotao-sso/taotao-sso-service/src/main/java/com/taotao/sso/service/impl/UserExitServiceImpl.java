package com.taotao.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.UserExitService;
/**
 * 安全退出，还未实现service与controller
 * @author Admin
 *
 */
public class UserExitServiceImpl implements UserExitService {
	
	@Autowired
	private JedisClient client;
	
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	
	
	@Override
	public TaotaoResult deleteUserByToken(String token) {
		
		//1.注入jedisclient
		
		//2.调用根据token查询用户信息（JSON）的方法  get方法
		String strjson = client.get(USER_INFO+":"+token);
		
		//3.判断是否查询到
		if(StringUtils.isNotBlank(strjson)) {
			//5.如果查询到  需要返回200  包含用户的信息  用户信息转成对象
			TbUser user = JsonUtils.jsonToPojo(strjson,TbUser.class);
			//重新设置过期时间
			return TaotaoResult.ok(user);
		}
		//4.如果查询不到  返回400
		return TaotaoResult.build(400,"用户已过期");
		
	}
}
