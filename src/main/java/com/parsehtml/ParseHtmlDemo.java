package com.parsehtml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhang.wenhan
 * @description ParseHtmlDemo
 * @date 2019/9/29 15:30
 */
public class ParseHtmlDemo {

    public static AtomicInteger rows = new AtomicInteger(1);

    public static void main(String[] args) {
        XSSFWorkbook workbook = createWorkbook();
        XSSFSheet sheet = workbook.getSheet("sheet1");
        /*for (int i = 1; i <= 2; i++) {
            total = readHtmlToExcel(sheet, "C:/Users/AA/Desktop/页面处理/" + i + "(1).html", total);
        }*/
        readFile("C:/Users/AA/Desktop/核保呗数据", sheet);

        try {
            FileOutputStream out = new FileOutputStream("C:/Users/AA/Desktop/核保呗.xlsx");
            workbook.write(out);
            out.close();
            System.out.println( "总计行数为：" + rows.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readFile(String path, XSSFSheet sheet){
        File folder = new File(path);
        if (folder.exists()) {
            File[] fileArr = folder.listFiles();
            if (null == fileArr || fileArr.length == 0) {
                System.out.println("文件夹是空的!");
            } else {
                for (File file : fileArr) {
                    if (file.isDirectory()) {
                        System.out.println("文件夹:" + file.getAbsolutePath() + "，继续递归！");
                        readFile(file.getAbsolutePath(), sheet);
                    } else {
                        readHtmlToExcel(sheet, file);
                    }
                }
            }
        }
    }


    private static void readHtmlToExcel(XSSFSheet sheet, File file) {
        try {
            System.out.println("当前解析页面：" + file.getAbsolutePath());
            String fileName = file.getName();
            fileName = fileName.replace(".html", "");
            String[] names = fileName.split("-");
            Document document = Jsoup.parse(file, "utf-8");
            Elements questions = document.getElementsByClass("question-btn");
            List<JSONObject> list = new ArrayList<>();
            if(questions.size() > 0) {
                String question = questions.get(0).text();
                Elements tags = document.getElementsByClass("tag-con");
                String risk = tags.get(0).getElementsByClass("tags").text();
                String underwrite = tags.get(1).getElementsByClass("tags").text();
                Elements elements = document.getElementsByClass("product-big-card");
                for (Element element : elements) {
                    String name = element.getElementsByClass("name").first().text();
                    Elements infos = element.getElementsByClass("info").first().children();
                    String params = element.getElementsByClass("params").first().text();
                    Elements remarks = element.getElementsByClass("remark-div");
                    if(remarks.size() > 0){
                        for(Element remark : remarks){
                            JSONObject json = new JSONObject();
                            json.put("name", name);
                            for (Element info : infos) {
                                String str = info.text();
                                String[] strs = str.split("：");
                                json.put(strs[0], strs[1]);
                            }
                            json.put("params", params);
                            json.put("承保说明", remark.getElementsByTag("p").get(0).text());
                            Elements span =  remark.child(0).getElementsByTag("span");
                            if(span.size() == 1){
                                json.put("承保结论", span.get(0).text());
                            } else {
                                Elements div = remark.child(0).getElementsByTag("div");
                                if(div.get(0).children().size() > 1){
                                    json.put("承保结论", div.get(1).child(0).text());
                                    if(name.equals("前行无忧终身重大疾病保险")){
                                        System.out.println("123");
                                    }
                                    json.put("承保结论说明", div.get(1).child(1).text());
                                }
                            }
                            list.add(json);
                        }
                    } else {
                        JSONObject json = new JSONObject();
                        json.put("name", name);
                        for (Element info : infos) {
                            String str = info.text();
                            String[] strs = str.split("：");
                            json.put(strs[0], strs[1]);
                        }
                        json.put("params", params);
                        list.add(json);
                    }
                }
                System.out.println(JSON.toJSONString(list));
                writeToExcel(sheet, question, risk, underwrite, list, names);
            } else {
                JSONObject json = new JSONObject();
                list.add(json);
                writeToExcel(sheet,  "", "", "", list, names);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToExcel(XSSFSheet sheet, String question, String risk, String underwrite, List<JSONObject> list, String[] names) {
        for (JSONObject json : list) {
            Row row = sheet.createRow(rows.intValue());
            row.createCell(0).setCellValue(question);
            row.createCell(1).setCellValue(risk);
            row.createCell(2).setCellValue(underwrite);
            row.createCell(3).setCellValue(json.getString("name"));
            row.createCell(4).setCellValue(json.getString("承保公司"));
            row.createCell(5).setCellValue(json.getString("保障期间"));
            row.createCell(6).setCellValue(json.getString("可购买年龄"));
            row.createCell(7).setCellValue(json.getString("是否自费药"));
            row.createCell(8).setCellValue(json.getString("职业限制"));
            row.createCell(9).setCellValue(json.getString("最高保额"));
            row.createCell(10).setCellValue(json.getString("赔付次数"));
            row.createCell(11).setCellValue(json.getString("承保说明"));
            row.createCell(12).setCellValue(json.getString("承保结论"));
            row.createCell(13).setCellValue(json.getString("承保结论说明"));
            row.createCell(14).setCellValue(names[0]);
            if(names.length ==2){
                row.createCell(15).setCellValue(names[1]);
            } else {
                row.createCell(15).setCellValue(names[1] + "-" + names[2]);
            }
            rows.incrementAndGet();
        }
    }

    private static XSSFWorkbook createWorkbook() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            XSSFSheet sheet = workbook.createSheet("sheet1");
            Row head = sheet.createRow(0);
            head.createCell(0).setCellValue("疾病名称");
            head.createCell(1).setCellValue("险种");
            head.createCell(2).setCellValue("承保");
            head.createCell(3).setCellValue("产品名称");
            head.createCell(4).setCellValue("承保公司");
            head.createCell(5).setCellValue("保障期间");
            head.createCell(6).setCellValue("可购买年龄");
            head.createCell(7).setCellValue("是否自费药");
            head.createCell(8).setCellValue("职业类别");
            head.createCell(9).setCellValue("最高保额");
            head.createCell(10).setCellValue("赔付次数");
            head.createCell(11).setCellValue("承保说明");
            head.createCell(12).setCellValue("承保结论");
            head.createCell(13).setCellValue("承保结论说明");
            head.createCell(14).setCellValue("大类");
            head.createCell(15).setCellValue("疾病类别");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

}
