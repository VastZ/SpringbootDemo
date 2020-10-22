package com.zoro.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoro.dto.OldLost;
import com.zoro.mapper.ClaimMapper;
import com.zoro.service.OldLostService;

/**
 * @author zhang.wenhan
 * @description OldLostServiceImpl
 * @date 2019/7/18 17:15
 */
@Service
public class OldLostServiceImpl implements OldLostService{

    @Autowired
    ClaimMapper mapper;

    public static final  String  URL = "https://ssl.700du.cn/erpwebservice.shtml";

    @Override
    @Transactional(timeout = 30, rollbackFor = Exception.class)
    public void sendRequest(OldLost oldLost) {
        doPost(URL, oldLost.getRequest());
        mapper.updateOldLostData(oldLost.getId());
    }

    private static String doPost(String url, String json) {
        String res = null;
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        try {
            CloseableHttpClient client = HttpClientBuilder.create().setConnectionTimeToLive(15, TimeUnit.SECONDS)
                    .build();
            // 构造消息头 // 发送Json格式的数据请求
            // post.setHeader("Content-type", APPLICATION_JSON_UTF8_VALUE);
            StringEntity entity = new StringEntity(json, "GBK");
            entity.setContentType(MediaType.TEXT_XML_VALUE);
            post.setEntity(entity);
            response = client.execute(post);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                res = EntityUtils.toString(response.getEntity());
            System.out.println("返回数据： " + res);
//            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        File file = new File("C:/Users/AA/Desktop/9.2-10.7老系统长险续期-需补充(1).xlsx");
        InputStream is = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            is = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet("data");
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<root>\n" +
                        "    <Type>CHECKPAYVOUCHER</Type>\n" +
                        "    <ERPPolicyKey>" + row.getCell(0).getStringCellValue() + "</ERPPolicyKey>\n" +
                        "    <PayVoucherKey>" + row.getCell(1).getStringCellValue() + "</PayVoucherKey>\n" +
                        "    <BizType>" + String.format("%.0f", row.getCell(2).getNumericCellValue()) + "</BizType>\n" +
                        "    <CompKey>" + row.getCell(3).getStringCellValue() + "</CompKey>\n" +
                        "    <CSUM>" + row.getCell(10).getNumericCellValue() + "</CSUM>\n" +
                        "    <prePolicyCode>" + row.getCell(4).getStringCellValue() + "</prePolicyCode>\n" +
                        "    <policyCode>" + row.getCell(13).getStringCellValue() + "</policyCode>\n" +
                        "    <policyYear>" + String.format("%.0f",row.getCell(19).getNumericCellValue()) + "</policyYear>\n" +
                        "    <timeperYear>" + String.format("%.0f",row.getCell(20).getNumericCellValue()) + "</timeperYear>\n" +
                        "    <prodName>" + row.getCell(16).getStringCellValue() + "</prodName>\n" +
                        "    <prodKey>" + row.getCell(7).getStringCellValue() + "</prodKey>\n" +
                        "    <holder>" + row.getCell(5).getStringCellValue() + "</holder>\n" +
                        "    <insurant>" + row.getCell(6).getStringCellValue() + "</insurant>\n" +
                        "    <fee>" + row.getCell(17).getNumericCellValue() + "</fee>\n" +
                        "    <stype>" + String.format("%.0f", row.getCell(8).getNumericCellValue()) + "</stype>\n" +
                        "    <ackDate>" + sdf.format(row.getCell(9).getDateCellValue()) + "</ackDate>\n" +
                        "    <payDate>" + sdf.format(row.getCell(14).getDateCellValue()) + "</payDate>\n" +
                        "</root>";
                doPost(URL, str);
                System.out.println("当前执行第" + i + "行");
                System.out.println(str);
                System.out.println("-------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
