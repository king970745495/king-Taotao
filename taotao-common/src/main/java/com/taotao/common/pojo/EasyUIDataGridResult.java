package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;
/*
 * datagrid展示数据的pojo  包括商品的pojo
 */
public class EasyUIDataGridResult implements Serializable {
	
	private Integer total;
	private List rows;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
