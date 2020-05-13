package com.lenovo.ai.ccagent.nlp.zhpcnlp.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONObject;

/**
 * @author :zhangchen
 * @createDate :2019年5月28日 下午4:45:52
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class ProductTypeConfigTest {

    public static void main(String[] args) {
        readExcel("E:\\working_data\\new_project\\lena-pipeline\\software-freq-with-category\\小翼微信可能涉及产品范围.xlsx");
    
        outputFile("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\dictionary\\product-type-en-list.txt");
         
//         try {
//             BufferedReader br = new BufferedReader(new FileReader("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\test.txt"));
//             String line = "";
//             String outResult = "";
//             while ((line = br.readLine()) != null) {
//                 outResult += line.split(" ")[0] + "\r\n";
//             }
//             br.close();
//             
//             FileOutputStream fos = new FileOutputStream(new File("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\test.txt"));
//             fos.write(outResult.getBytes());
//             fos.close();
//           } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    
    private static void outputFile(String path) {
//        String str = "";
//        for (String key : excelData.keySet()) {
//             str += key + "\r\n";
//             for (String key2 : excelData.get(key).keySet()) {
//                 str += "  " + key2 + "\r\n";
//                 for (String v : excelData.get(key).get(key2)) {
//                     str += "    " + v + "\r\n";
//                 }
//             }
//        }
        File file = new File(path);
        try {
            if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(JSONObject.fromObject(excelData).toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Map<String, Set<String>>> excelData = new HashMap<>(8);
    
    private static void readExcel(String path){
        try {
            File excel = new File(path);
            if (excel.isFile() && excel.exists()) {   

                String[] split = excel.getName().split("\\.");  
                Workbook wb;
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.err.println("文件类型错误!");
                    return;
                }

                addData(wb);
            } else {
                System.err.println("找不到指定的文件");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    private static void addData(Workbook wb){
        // 开始解析
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets();
                sheetIndex ++) {
            Sheet sheet = wb.getSheetAt(sheetIndex);
            int firstRowIndex = sheet.getFirstRowNum() + 1;   
            int lastRowIndex = sheet.getLastRowNum();
            System.out.println("firstRowIndex: "+firstRowIndex + "=====lastRowIndex: "+lastRowIndex);
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    Cell c1 = row.getCell(1);
                    Cell c2 = row.getCell(2);
                    Cell c3 = row.getCell(3);
                    if (!c3.toString().matches(".*[\u4e00-\u9fa5]{1,}.*")) {
                        Map<String, Set<String>> map1 = excelData.get(c1.toString());
                        if (map1 == null) {
                            map1 = new HashMap<>(8);
                            excelData.put(c1.toString(), map1);
                        }
                        Set<String> set = map1.get(c2.toString());
                        if (set == null) {
                            set = new HashSet<>(8);
                            map1.put(c2.toString(), set);
                        }
                        set.add(c3.toString());
                    }
                }
            }
        }
       
    }
}



