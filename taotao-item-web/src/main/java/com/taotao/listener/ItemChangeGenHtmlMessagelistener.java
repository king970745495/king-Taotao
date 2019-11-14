package com.taotao.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 监听器
 * 获取消息
 * 执行生成静态页面的业务逻辑
 * 
 * @author Admin
 *
 */
public class ItemChangeGenHtmlMessagelistener implements MessageListener {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer configurer;
	
	@Override
	public void onMessage(Message message) {
		
		
		if(message instanceof TextMessage) {
			TextMessage message2=(TextMessage)message;
			
			try {
				//1.获取商品id
				Long itemId=Long.valueOf(message2.getText());
				
				//2.从数据库中获取数据，商品数据/可以调用manager中的服务  获取到了数据集
				TbItem tbItem = itemService.getItemById(itemId);
				Item item=new Item(tbItem);
				TbItemDesc itemDesc = itemService.getItemDescById(itemId);
				
				//3.生成静态页面  准备好  模板+数据集
				genHtmlFreeMarker(item,itemDesc);
				
				
				//4.
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

	//生成静态页面
	private void genHtmlFreeMarker(Item item, TbItemDesc itemDesc) throws Exception{
		
		//1.获取Configurer对象
		Configuration configuration=configurer.getConfiguration();
		//2.创建模板  获取模板文件对象
		Template template=configuration.getTemplate("item.ftl");
		
		//3.创建数据集
		Map  model=new HashMap<>();
		model.put("item", item);
		model.put("itemDesc", itemDesc);
		
		//4.输出
		Writer writer=new FileWriter(new File("D:/Java/freemarker"+"/"+item.getId()+".html"));
		template.process(model, writer);
		writer.close();
	}

}
