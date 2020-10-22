package com.mine.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@SuppressWarnings("resource")
public class EsProdTest {
    
    
    public final static String IndexName = "b2b2c2018";
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
//         client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("10.1.1.246", 9300));

//        client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.30.66", 9300));
         client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("10.1.1.246", 9300));
    }

    
    public static void main(String[] args) {
//        createMapping(IndexName, PRODUCTS_DocumentType);
        List<String> jsons = getData();
//        BulkRequestBuilder bulider  =  client.prepareBulk();
        for (String json : jsons) {
           
            JSONObject obj =JSON.parseObject(json);
            String uuid = obj.getString("uuid");
            System.out.println("同步数据" + uuid);
            client.prepareIndex(IndexName, PRODUCTS_DocumentType) .setId(uuid).setSource(json).execute().actionGet();
        }
        
    }
    
    public static List<String> getData() {
        List<String> jsons = new ArrayList<>(500);
        File source = new File("C:/Users/AA/Desktop/source_prd.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(source));
            String temp;
            while (StringUtils.isNotEmpty(temp = reader.readLine())) {
                jsons.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取文件失败");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsons;
    }
    
    
    public static  void createMapping(String indices, String mappingType) {
        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder()

                    .startObject()

                    .startObject(PRODUCTS_DocumentType)

                    .startObject("properties")

                    .startObject("price").field("type", "double").field("store", "yes").endObject()

                    .endObject()

                    .endObject()

                    .endObject();
            PutMappingRequest mapping = Requests.putMappingRequest(IndexName)
                    .type(PRODUCTS_DocumentType).source(builder);

            client.admin().indices().putMapping(mapping).actionGet();

        } catch (IOException e) {
          
        }

    }
}
