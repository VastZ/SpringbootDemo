package com.generateHtml;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.math.BigDecimal;

/**
 * @author zhang.wenhan
 * @description HtmlGenerator
 * @date 2020/10/22
 */
public class HtmlGenerator {

    public static void main(String[] args) {
        File file = new File("C:/Users/admin/Desktop/1022-4.xlsx");
        try (FileInputStream fis = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("4");
            int last = sheet.getLastRowNum();
            for (int i = 1; i <= last; i++) {
                Row row = sheet.getRow(i);
                parseExcel(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseExcel(String title, String img, String detail) {
        String html = "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0\" />\n" +
                "\t<meta name=\"renderer\" content=\"webkit\">\n" +
                "\t<title>{title}</title>\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/pc-detail.css\"/>\n" +
                "</head>\n" +
                "<script type=\"text/javascript\"> \n" +
                "var isMobile = /iphone|android|ucweb|ucbrowser|nokia|sony|ericsson|mot|samsung|sgh|lg|philips|panasonic|alcatel|lenovo|cldc|midp|wap|mobile/i.test(navigator.userAgent.toLowerCase());\n" +
                "/*if(!isMobile) {\n" +
                "document.location.href = 'http://124.70.70.45/detail/index3-1.html';\n" +
                "}*/\n" +
                "</script>\n" +
                "<body>\n" +
                "\t<section class=\"section-wrap\">\n" +
                "\t\t<div class=\"section section-3\" id=\"liebiao\">\n" +
                "            <div class=WordSection1 style='layout-grid:15.6pt'>\n" +
                "\t\t\t\t<p class=MsoNormal align=center style='text-align:center;tab-stops:30.85pt'>\n" +
                "\t\t\t\t\t<span style='font-size:16.0pt;font-family:黑体;mso-bidi-font-family:黑体'>{title}</span>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p class=MsoNormal align=center style='text-align:center'>\n" +
                "\t\t\t\t\t<b style='mso-bidi-font-weight: normal'>\n" +
                "\t\t\t\t\t\t<span lang=EN-US style='mso-bidi-font-size:10.5pt;font-family:仿宋; mso-bidi-font-family:仿宋;color:black;background:white;mso-font-kerning:0pt; mso-no-proof:yes'>\n" +
                "\t\t\t\t\t\t\t<![if !vml]>\n" +
                "\t\t\t\t\t\t\t<img width=317 height=238 src=\"../img/{img}.jpg\" >\n" +
                "\t\t\t\t\t\t\t<![endif]>\n" +
                "\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t</b>\n" +
                "\t\t\t\t</p>\n" +
                "<p class=MsoNormal style='text-indent:32.0pt;mso-char-indent-count:2.0; line-height:28.0pt;mso-line-height-rule:exactly;vertical-align:baseline'>\n" +
                "\t<span style='font-size:16.0pt;font-family:仿宋;mso-bidi-font-family:仿宋'>\n" +
                "\t{detail}\n" +
                "\t</span>\n" +
                "</p>\n" +
                "\n" +
                "</div>\n" +
                "</div> \n" +
                "\t\t\n" +
                "\t</section> \n" +
                "\n" +
                "  \n" +
                "</body>\n" +
                "</html>";
        html = html.replace("{title}", title);
        html = html.replace("{img}", img);
        html = html.replace("{detail}", detail);
        generateFile(html, img);
    }

    public static void generateFile(String html, String fileName) {
        try {
            FileWriter fw = new FileWriter("C:/Users/admin/Desktop/test/pc/" + fileName + ".html", true);
            fw.write(html);
            fw.flush();// flush()方法刷新缓冲区
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
