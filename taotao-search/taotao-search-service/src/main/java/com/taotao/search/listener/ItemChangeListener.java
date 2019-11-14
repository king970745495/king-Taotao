package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
/**
 * 接收消息的监听器
 * @author Admin
 *
 */
public class ItemChangeListener implements MessageListener {
	
	@Autowired
	private SearchService service;
	
	@Override
	public void onMessage(Message message) {
		//判断消息的类型是否为textMessage
		//如果是获取商品的id
		String text="";
		if(message instanceof TextMessage) {
			try {
				text = ((TextMessage) message).getText();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		//通过商品的id查询数据，需要开发mapper  通过id查询数据
		//更新索引库
		try {
			TaotaoResult result = service.updateSearchItemById(Long.valueOf(text));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
