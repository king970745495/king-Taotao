package com.taotao.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {
	@Test
	public void add() throws SolrServerException, IOException {
		//1.创建solrjserver  建立连接  需要地址
		//org.apache.solr.client.solrj.impl.HttpSolrServer.HttpSolrServer(String)
		SolrServer server=new HttpSolrServer("http://192.168.25.133:8080/solr");
		//2.创建solrjinputdocumentt
		SolrInputDocument document=new SolrInputDocument();
		//3.想文档中添加域
		document.addField("id", "test01");
		document.addField("item_title", "这是一个测试");
		//4.将文档提交到索引库中
		server.add(document);
		//5.提交
		server.commit();
	}
	
	@Test
	public void testQuery() throws Exception {
		//1.创建solrServer对象
		SolrServer server=new HttpSolrServer("http://192.168.25.133:8080/solr");
		//2.创建solrQuery对象
		SolrQuery query=new SolrQuery();
		//3.设置查询条件
		query.setQuery("阿尔卡特");
		query.addFilterQuery("item_price:[0 TO 300000000]");
		query.set("df", "item_title");
		//4.执行查询，获取结果集
		QueryResponse response = server.query(query);
		//5.便利结果集打印
		SolrDocumentList results = response.getResults();
		System.out.println("查询的总记录数"+results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
		}
	}
	
	
}
