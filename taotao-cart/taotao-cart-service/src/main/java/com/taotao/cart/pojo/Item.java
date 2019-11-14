package com.taotao.cart.pojo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taotao.pojo.TbItem;

public class Item extends TbItem{
	public Item(TbItem item) {
		BeanUtils.copyProperties(item, this);//将原来的数据拷贝到item有的属性中
	}
	@JsonIgnore
	public String[] getImages() {
		
		if(StringUtils.isNotBlank(super.getImage())) {
			return super.getImage().split(",");
		}
		return null;
	}
}
