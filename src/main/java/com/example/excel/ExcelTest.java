package com.example.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;

/**
 * @author zhang.wenhan
 * @description ExcelTest
 * @date 2019/9/6 9:24
 */
public class ExcelTest {
    public static void main(String[] args) {
        printSql();
    }

    /*
    1.  select * from withdraw_apply where bankNo in () and state = '1' and applyMoney in () order by applyMoney ;

    2.  select wa.uuid, wa.accountName, wa.applyMoney, ca.uuid as caUuid, ca.accountBalance, v.uuid as vUuid from withdraw_apply wa, customer_account ca,
    virtual_account_customer_log v
    where  wa.userUuid = ca.customerUuid
    and  wa.withdrawNo = v.documentNo
    and v.frozenState = 1
    and wa.uuid in ( ) order by wa.applyMoney;
    3. 最后一列为商户备注 记得排序匹配
    */
    private static void printSql() {
        File file = new File("C:/Users/AA/Desktop/1234.xlsx");
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Sheet1");
            int last = sheet.getLastRowNum();
            for (int i = 1; i <= last; i++) {
                Row row = sheet.getRow(i);
                System.out.println("update withdraw_apply set state = '2', note = '"
                        + row.getCell(6).getStringCellValue()
                        + "' where uuid = '" +
                        row.getCell(0).getStringCellValue() + "';");
                Double money = Double.valueOf(row.getCell(2).getNumericCellValue());
                Double accountBanlance = Double.valueOf(row.getCell(4).getNumericCellValue());
                double banlance = BigDecimal.valueOf(money).add(BigDecimal.valueOf(accountBanlance)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println("update customer_account set accountBalance = accountBalance + "
                        + money
                        + " where uuid = '" +
                        row.getCell(3).getStringCellValue() + "';");
                System.out.println("update virtual_account_customer_log set operType = '0', description = '"
                        + row.getCell(6).getStringCellValue()
                        + "', nowbalance = '" + banlance
                        + "' where uuid = '" +
                        row.getCell(5).getStringCellValue() + "';");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
