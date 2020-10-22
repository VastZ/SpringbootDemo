package com.zoro.service.impl;

import com.zoro.dto.WaitAck;
import com.zoro.mapper.AckMapper;
import com.zoro.service.WaitAckService;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhang.wenhan
 * @description WaitAckServiceImpl
 * @date 2019/9/18 9:53
 */
@Service
public class WaitAckServiceImpl implements WaitAckService {

    @Autowired
    AckMapper ackMapper;

    @Override
    public void lifeAck(WaitAck waitAck) {
        if(waitAck == null){
            return ;
        }
        String url = "https://700du.cn/store/sendToErp/lifeAck?orderId=" + waitAck.getOrderId();
        String res = doPost(url);
        if("succ-对接成功！".equals(res)){
            waitAck.setResultCode("推送成功");
        } else {
            waitAck.setResultCode("推送失败");
            waitAck.setResultInfo(res);
        }
        ackMapper.updateById(waitAck);
    }


    private static String doPost(String url) {
        String res = null;
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        try {
            CloseableHttpClient client = HttpClientBuilder.create().setConnectionTimeToLive(60, TimeUnit.SECONDS)
                    .build();
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                res = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = "推送异常" + url;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

}
