package com.zoro.controller;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elastic")
public class ElasticSearchController {
    
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
        client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.30.66", 9300));
    }
    
    public static int num = 0;
    
    @PostMapping("/query")
    public String query(){
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        BoolQueryBuilder query3 = QueryBuilders.boolQuery();
        String str = "平安" + num;
        
        if(StringUtils.isNotEmpty(str)){
            query3.should(QueryBuilders.wildcardQuery("desc", "*"+str.toLowerCase()+"*"));
            query3.should(QueryBuilders.matchPhraseQuery("desc", str));    
        }
        query.must(query3);
        
        SearchResponse searchResponse = client.prepareSearch(IndexName)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes(PRODUCTS_DocumentType).setQuery(query)
                .execute().actionGet();
        System.out.println("当前查询结果数量为：" + searchResponse.getHits().getTotalHits());
        
        num++;
        
        return "";
    }

}
