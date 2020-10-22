package com.mine.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

//import com.aebiz.b2b2c.search.product.vo.ProductModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("resource")
public class EsTest {

    public final static String IndexName = "b2b2c2016";
    public final static String PRODUCTS_DocumentType = "products";

    public static Client client = null;

    static {
        Settings settings = ImmutableSettings.settingsBuilder()
                // 指定集群名称
                .put("cluster.name", "elasticsearch")
                // 探测集群中机器状态
                .put("client.transport.sniff", true).build();
        /*
         * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象 用完记得要关闭
         */
         client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.30.116", 9100));

//        client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.30.66", 9300));
//         client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("101.200.164.217", 9300));
    }

    public static void main(String[] args) {
//        JSONObject productModel = getById("470684c6ff2847b59d18e4ef70b911d3");
        // change(productModel);
        getAll();
        
    }

    public static JSONObject getById(String id) {
        GetResponse result = client.prepareGet(IndexName, PRODUCTS_DocumentType, id).get();
        String json = JSON.toJSONString(result.getSource());
        System.out.println("查询结果: " + json);
        JSONObject productModel = JSON.parseObject(json);
        return productModel;
    }

    public static void change(JSONObject productModel) {
        DeleteResponse res = client.prepareDelete().setIndex(IndexName).setType(PRODUCTS_DocumentType)
                .setId(productModel.getString("uuid")).get();
        System.out.println("删除结果" + JSON.toJSONString(res));
        // 将数据同步至ES服务器
        productModel.put("state", "1");
        String json = JSON.toJSONString(productModel);
        client.prepareIndex(IndexName, PRODUCTS_DocumentType).setId(productModel.getString("uuid")).setSource(json)
                .get();
    }

    public static void getAll() {
        SearchRequestBuilder srb = client.prepareSearch(IndexName).setTypes(PRODUCTS_DocumentType);
        QueryBuilder qb =   QueryBuilders.matchAllQuery();
        SearchResponse sr = srb.setQuery(qb).setFrom(0).setSize(1000).execute().actionGet(); // 查询所有
        SearchHits hits = sr.getHits();
        System.out.println(hits.getTotalHits());
        File file = new File("C:/Users/AA/Desktop/source_prd.txt");
        FileOutputStream fos = null;
        try {
             fos = new FileOutputStream(file);
            for (SearchHit hit : hits) {
                fos.write((hit.getSourceAsString() + "\n").getBytes());
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    
    
}
