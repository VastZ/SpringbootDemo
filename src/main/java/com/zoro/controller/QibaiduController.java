package com.zoro.controller;

import com.zoro.service.QibaiduService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author zhang.wenhan
 * @description QibaiduController
 * @date 2019/12/2 13:11
 */
@RestController
@RequestMapping("/qibaidu")
public class QibaiduController {

    @Autowired
    QibaiduService qibaiduService;

    @GetMapping("/dealWithdrawApply")
    public String dealWithdrawApply(){
        File file = new File("C:/Users/AA/Desktop/123.xlsx");
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Sheet1");
            int last = sheet.getLastRowNum();
            for (int i = 1; i <= last; i++) {
                Row row = sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                String applyMoney = row.getCell(1).getStringCellValue();
                String note = row.getCell(2).getStringCellValue();
                qibaiduService.dealWithdrawApply(name, applyMoney, note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
