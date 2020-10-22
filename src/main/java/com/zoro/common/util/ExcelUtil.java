package com.zoro.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * excel 相关操作工具类
 * @author zhang.wenhan
 * @description ExcelUtil
 * @date 2019/9/5 13:10
 */
public class ExcelUtil {

    public void createExcel(String fileName){
        File excel = new File("C:/Users/AA/Desktop/request.xls");//Excel文件生成后存储的位置。
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell00 = row0.createCell(0);
        cell00.setCellValue("erp主键");
        XSSFCell cell01 = row0.createCell(1);
        cell01.setCellValue("请求报文");
    }

    public XSSFWorkbook getWorkbook(String fileName){
        try(FileInputStream fis = new FileInputStream(fileName)){
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            return workbook;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public XSSFSheet getSheet(XSSFWorkbook workbook, String sheetName){
        return  workbook.getSheet(sheetName);
    }

    public XSSFRow getRow(XSSFSheet sheet, int rownum){
       return  sheet.getRow(rownum);
    }

    public String getCellStringValue(XSSFRow row, int cellnum){
        CellType cellType = row.getCell(cellnum).getCachedFormulaResultTypeEnum();
        Cell cell = row.getCell(cellnum);
        switch (cellType){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return Double.valueOf(cell.getNumericCellValue()).toString();
            case BOOLEAN:
                return Boolean.valueOf(cell.getBooleanCellValue()).toString();
            case FORMULA:
                return cell.getStringCellValue();
            default:
                return "";
        }

    }

}
