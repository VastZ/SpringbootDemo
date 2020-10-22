package com.zoro.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author zhang.wenhan
 * @description ExcelTest
 * @date 2019/9/6 9:24
 */
public class ExcelTest {
    public static void main(String[] args) {
        printProvince();
        printCity();
        printRegion();
    }

    private static void printProvince() {
        File file = new File("C:/Users/AA/Desktop/产品需求/长生吉星/14. 地区代码(1)/地区代码/省.xls");
        try (FileInputStream fis = new FileInputStream(file)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("省");
            int last = sheet.getLastRowNum();
            for (int i = 0; i <= last; i++) {
                Row row = sheet.getRow(i);
                System.out.println("INSERT INTO company_area_province(uuid,provinceName,templateId) VALUES('"
                        + row.getCell(0).getStringCellValue() + "','" + row.getCell(1).getStringCellValue()
                        + "','areaTemplateCSRS');");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printCity() {
        File file = new File("C:/Users/AA/Desktop/产品需求/长生吉星/14. 地区代码(1)/地区代码/市.xls");
        try (FileInputStream fis = new FileInputStream(file)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("市");
            int last = sheet.getLastRowNum();
            for (int i = 0; i <= last; i++) {
                Row row = sheet.getRow(i);
                System.out.println("INSERT INTO company_area_city(uuid,provinceUuid,cityName,templateId) VALUES('"
                        + row.getCell(0).getStringCellValue() + "','" + row.getCell(0).getStringCellValue().substring(0, 3) + "000"
                        + "','" + row.getCell(1).getStringCellValue() + "','areaTemplateCSRS');");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printRegion() {
        File file = new File("C:/Users/AA/Desktop/产品需求/长生吉星/14. 地区代码(1)/地区代码/区.xls");
        try (FileInputStream fis = new FileInputStream(file)) {
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("区");
            int last = sheet.getLastRowNum();
            for (int i = 0; i <= last; i++) {
                Row row = sheet.getRow(i);
                System.out.println("INSERT INTO company_area_region(uuid,cityUuid,regionName,templateId) VALUES('"
                        + row.getCell(0).getStringCellValue() + "','" + row.getCell(0).getStringCellValue().substring(0, 4) + "00"
                        + "','" + row.getCell(1).getStringCellValue() + "','areaTemplateCSRS');");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
