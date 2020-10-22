package com.example.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;

/**
 * @author zhang.wenhan
 * @description ExcelTest
 * @date 2019/9/6 9:24
 */
public class ExcelDemo {
    public static void main(String[] args) {
        String str = "sucess#bus15782995749081669#0#01#bus15782995749081669";
        String[] strs = str.split("#");
        System.out.println(strs[4]);
//        printSql();
    }

    private static void printSql() {
        File file = new File("C:/Users/AA/Desktop/erp_fail.xlsx");
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Sheet1");
            int last = sheet.getLastRowNum();
            for (int i = 1; i <= last; i++) {
                Row row = sheet.getRow(i);
                String request = row.getCell(1).getStringCellValue();
                JSONObject jsonObject = XML.toJSONObject(request);
                Double feeSum = jsonObject.getJSONObject("ERP").getJSONObject("ROOT").getJSONObject("BASEINFO").getDouble("FEESUM");
                Double fee = 0d;
                try {
                    JSONArray array = jsonObject.getJSONObject("ERP").getJSONObject("ROOT").getJSONObject("INSURANTS").getJSONArray("INSURANT");
                    for (int j = 0; j < array.length(); j++) {
                        JSONObject insurant = array.getJSONObject(j);
                        fee += insurant.getJSONObject("PRODS").getJSONObject("PROD").getDouble("FEESUM");
                    }
                } catch (Exception e) {
                    fee += jsonObject.getJSONObject("ERP").getJSONObject("ROOT").getJSONObject("INSURANTS").getJSONObject("INSURANT")
                            .getJSONObject("PRODS").getJSONObject("PROD").getDouble("FEESUM");
                }
                    System.out.println(jsonObject.getJSONObject("ERP").getString("PREPOLICYCODE"));
                /*if(feeSum.doubleValue() != fee.doubleValue()){
                    System.out.println(jsonObject.getJSONObject("ERP").getString("PREPOLICYCODE") + " " + feeSum + " " + fee);
                }*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
