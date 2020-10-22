package com.example.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baidu.aip.ocr.AipOcr;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

public class QualificationDemo {
    
    private static final Logger logger = LoggerFactory.getLogger("com.aebiz");
    
    //2019-04-08改版后入口 http://iir.circ.gov.cn
    final static String URL_FIRST = "http://iir.circ.gov.cn/ipq/insurance.do?checkcaptch";   
    final static String URL_SECOND = "http://iir.circ.gov.cn/ipq/certiInfo.do?checkcaptch";

    private static CookieStore store;
    
    public static void main(String[] args) throws Exception {
        /*String name = "岳建英";
        String idNo = "320521196209291728";
        String mobile = "15111112222";
        QualificationDemo demo = new QualificationDemo();
        demo.checkQualificationNew(name, idNo, mobile);*/
//        test();
        doGet("http://iir.circ.gov.cn/ipq/captchacn.svl", "utf-8", true, "1234");
    }

    private static String getCookieFromResponse(@NonNull HttpResponse response) {
        Header[] responseHeader = response.getHeaders("Set-Cookie");
        int length = responseHeader.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (responseHeader[i] != null) {
                if ("Set-Cookie".equals(responseHeader[i].getName())) {
                    int index = responseHeader[i].getValue().indexOf(";");
                    if (i == length - 1) {
                        stringBuilder.append(responseHeader[i].getValue().substring(0, index));
                    } else {
                        stringBuilder.append(responseHeader[i].getValue().substring(0, index) + "; ");
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    @SuppressWarnings("deprecation")
    private static HttpResponse doGet(String url, String charset, boolean isSetCookie, String cookieValue) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(store).build();
        HttpGet httpGet = new HttpGet(url);
        if (isSetCookie) {
            httpGet.addHeader("Cookie", cookieValue);
        }
        HttpResponse response = client.execute(httpGet);
        if (response != null) {
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_PRECONDITION_FAILED) {
                return doGet(url, charset, true, getCookieFromResponse(response));
            } else if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                return response;
            }
        }
        return response;
    }

    public static void test () throws IOException {
        store= new BasicCookieStore();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(store).build();
        HttpGet httpGet =  new HttpGet("http://iir.circ.gov.cn/ipq/captchacn.svl");
        httpGet.addHeader("","");
        ShallowEtagHeaderFilter filter = new ShallowEtagHeaderFilter();
//        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        CloseableHttpResponse response = client.execute(httpGet);
        //打印返回值
        String  result = EntityUtils.toString(response.getEntity());
        System.out.println(result);
        List<Cookie> cookielist = store.getCookies();
        System.out.println("大小为" + cookielist.size());
        for(Cookie cookie: cookielist){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println("cookie name =" + name);
            System.out.println("Cookie name=" + value);
        }

    }
    
    public Map<String, String> checkQualificationNew(String name, String idNo, String mobile) {
        Map<String, String> resMap = new HashMap<String, String>();
        Map<String, String> saveMap = new HashMap<String, String>();
        saveMap.put("name", name);
        saveMap.put("idNo", idNo);
        saveMap.put("mobile", mobile);
        try {
            logger.info("资质校验开始,姓名:{}, 身份证:{}, 注册用电话号码:{} ",name, idNo, mobile);
            // 身份证号转换小写为答谢
            idNo = idNo.toUpperCase();
            // 根据身份证取得性别
            // 每次用户请求会有5次失败限制
            for (int i = 1; i < 6; i++) {
                String response = sendPostToGov(name, idNo, URL_FIRST);
                logger.info("URL_FIRST:{} 查询结束,返回结果res:{}", URL_FIRST, response);
                saveMap.put("DATA", response);
                // 查询失败 ,其他原因。重新查询。
                if(response.contains("_Fail")){
                    saveMap.put("Flag", "-1");
                    saveQualifyResult(saveMap);
                    continue;
                }
                JSONObject res = JSON.parseObject(response);
                if("fail".equals(res.getString("result"))){
                    saveMap.put("Flag", "-1");
                    saveQualifyResult(saveMap);
                    continue;
                }
                // 如果成功
                JSONObject dataGrid = res.getJSONObject("dataGrid");
                // 如果查询结果>0 存在查询结果
                if(dataGrid.getInteger("total") > 0){
                    JSONObject obj = dataGrid.getJSONArray("rows").getJSONObject(0);
                    saveMap.put("qualificationCertificate", obj.getString("ualificano"));
                    saveMap.put("employmentCertificate",  obj.getString("practicecode"));
                    if(dataGrid.getInteger("total") == 1){
                        saveMap.put("Flag", "0");
                    } else {
                        saveMap.put("Flag", "1");
                    }
                    String resUuid = saveQualifyResult(saveMap);
                    resMap.put("UUID", resUuid);
                    resMap.put("flag", saveMap.get("Flag"));
                    return resMap;
                }
                // 查询成功 无结果, 则查询第二个认证地址
                if (dataGrid.getInteger("total") == 0) { 
                    for (int j = 1; j < 6; j++) {
                        String responseSec = sendPostToGov(name, idNo, URL_SECOND);
                        logger.info("URL_SECOND:{}, 查询结束,返回结果res:{}", URL_SECOND ,response);
                        saveMap.put("DATA", responseSec);
                        // 查询失败 ,其他原因。重新查询。
                        if(response.contains("_Fail")){
                            saveMap.put("Flag", "-1");
                            saveQualifyResult(saveMap);
                            continue;
                        }
                        JSONObject resSec = JSON.parseObject(responseSec);
                        if("fail".equals(resSec.getString("result"))){
                            saveMap.put("Flag", "-1");
                            saveQualifyResult(saveMap);
                            continue;
                        }
                        // 如果成功
                        JSONObject dataGridSec = resSec.getJSONObject("dataGrid");
                        if (dataGridSec.getInteger("total") > 0) {
                            JSONObject obj = dataGridSec.getJSONArray("rows").getJSONObject(0);
                            saveMap.put("qualificationCertificate", obj.getString("ualificano"));
                            saveMap.put("employmentCertificate",  obj.getString("practicecode"));
                            if(dataGrid.getInteger("total") == 1){
                                saveMap.put("Flag", "0");
                            } else {
                                saveMap.put("Flag", "1");
                            }
                            String resUuid = saveQualifyResult(saveMap);
                            resMap.put("UUID", resUuid);
                            resMap.put("flag", saveMap.get("Flag"));
                            return resMap;
                        }
                        if (dataGridSec.getInteger("total") == 0) { // 查询成功 无信息
                            System.out.println("查询到空结果，此人无可用资质证明");
                            logger.info("查询到空结果，此人无可用资质证明, name:{}, idNo:{}", name, idNo);
                            saveMap.put("Flag", "2");
                            saveQualifyResult(saveMap);
                            resMap.put("UUID", null);
                            resMap.put("flag", "2");
                            return resMap;
                        }
                    }
                    break;
                }
            }
            // 返回超过次数限制，请稍后重试
            logger.info("资质失败 ，超过指定查询失败次数");
            resMap.put("UUID", null);
            resMap.put("flag", "3");
            return resMap;
        } catch (Exception e) {
            logger.error("资质认证异常, name:" + name + "idNo:" + idNo, e);
            resMap.put("UUID", null);
            resMap.put("flag", "-2");
        }
        return resMap;
    }
    
    private String sendPostToGov(String name, String idNo, String url) {
        String res = null;
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        ByteArrayOutputStream os = null;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().setConnectionTimeToLive(15, TimeUnit.SECONDS)
                    .build();
            HttpGet httpGet = null;
            try {
                httpGet = new HttpGet("http://iir.circ.gov.cn/ipq/captchacn.svl");
            } catch (Exception e) {
                return "Get_Pic_Connection_Fail";
            }
            logger.info("--------------开始获取图片验证码-------------");
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.info("获取图片验证码失败！请检查保监会验证码接口. Method failed:{}", response.getStatusLine());
                return "Get_Pic_Connection_Fail";
            }
            os = new ByteArrayOutputStream();
            response.getEntity().writeTo(os);
            String resPicCode = this.getCodeFromPic(os.toByteArray());
            logger.info("baidu识图返回报文,返回内容为:{}", resPicCode);
            if ("Get_Pic_Response_Fail".equals(resPicCode)) {
                // 百度识图返回报文解析错误。
                logger.info("baidu识图返回报文解读失败,返回内容为: ", resPicCode);
                return resPicCode;
            }

            List<BasicNameValuePair> nvps = new ArrayList <>();
            BasicNameValuePair nvp1 = new BasicNameValuePair("name", name);
            BasicNameValuePair nvp2 = new BasicNameValuePair("cardno", idNo.substring(14, idNo.length()));
            BasicNameValuePair nvp3 = new BasicNameValuePair("captcha", resPicCode);
            nvps.add(nvp1);
            nvps.add(nvp2);
            nvps.add(nvp3);

            logger.info("baidu识图返回报文,返回内容为:{}", resPicCode);
            if ("Get_Pic_Response_Fail".equals(resPicCode)) {
                // 百度识图返回报文解析错误。
                logger.info("baidu识图返回报文解读失败,返回内容为: ", resPicCode);
                return resPicCode;
            }
            UrlEncodedFormEntity  entity=new UrlEncodedFormEntity(nvps, Consts.UTF_8);
            entity.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            post.setEntity(entity);
            
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                res = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("执行http请求异常", "url:" + url , e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                    os.close();
                } catch (IOException e) {
                    logger.error("response 关闭异常", "url:" + url, e);
                }
            }
        }
        return res;
    }
    
    
    public  String getCodeFromPic(byte[] fileStream) {
        try {
            JSONObject apiRes = JSON.parseObject(this.getAPI(fileStream));
            JSONArray resJSONArray = apiRes.getJSONArray("words_result");
            return resJSONArray.getJSONObject(0).getString("words").replaceAll(" ", "");
        } catch (Exception e) {
            return "Get_Pic_Response_Fail";
        }
    }
    
    public String getAPI(byte[] fileStream) throws JSONException {
        // 初始化一个AipOcr
        // 可选：设置网络连接参数
        AipOcr client = SingletonBaiduAPI.getInstance();
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        logger.info("调用百度图片识别API.");
        // 调用接口 传入图片流
        org.json.JSONObject res = client.basicGeneral(fileStream, new HashMap<String, String>());
        logger.info("识图结果res:{}", res.toString());
        return res.toString(2);
    }
    
    /**
     * 保存检查结果
     */
    public String saveQualifyResult(Map<String, String> saveMap) {
        // TODO save result 
        return "";
    }
}

class SingletonBaiduAPI {
    private SingletonBaiduAPI() {
    };

    private static class SingletonHolder {
        static String APP_ID = "11555830";
        static String API_KEY = "WZjZdx6k4kDupMlOFNgoNzHV";
        static String SECRET_KEY = "5OBbMnZNq8EcfIRG8kcdTVWhOeGxYWCU";
        private static final AipOcr INSTANCE = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }

    public static final AipOcr getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
