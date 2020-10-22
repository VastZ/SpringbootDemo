package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class LongRenewalTest {

    public static void main(String[] args) {
        List<String> uuids = Arrays.asList("1", "2", "3");

        System.out.println(StringUtils.join(uuids, ","));

//        printSql();

    }

    private static void printSql() {
        File file = new File("C:/Users/Admin/Desktop/长险续期佣金.xlsx");
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Sheet1");
            int last = sheet.getLastRowNum();
            for (int i = 1; i <= last; i++) {
                Row row = sheet.getRow(i);
                String uuid = row.getCell(3).getStringCellValue();
                String renewRateInfo = row.getCell(5).getStringCellValue();
                double rate = row.getCell(7).getNumericCellValue();
                JSONObject jsonObject = JSON.parseObject(renewRateInfo);
                jsonObject.put("obposition0000000092", rate);
                jsonObject.put("obposition0000000093", rate);
                System.out.println("update commission_job_rate_long set renewRateInfo = '" + JSON.toJSONString(jsonObject) + "' where uuid = '" + uuid + "';");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}