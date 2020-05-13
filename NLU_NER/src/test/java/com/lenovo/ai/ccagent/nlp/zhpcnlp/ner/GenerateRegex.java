package com.lenovo.ai.ccagent.nlp.zhpcnlp.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhangchen
 * @createDate :2019年6月4日 上午11:05:17
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class GenerateRegex {

    public static void main(String[] args) {
        List<String> readList = readFile("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\test.txt");
        List<String> resultList = new ArrayList<>(16);
        for (String s : readList) 
            resultList.add(generateRegex(s));
        outFile(resultList, "E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\test.txt");
    }
    
    private static String generateRegex(String str){
        String result = "";
        char[] charArray = str.toLowerCase().toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ') continue;
            if (charArray[i] == '.')
                result += "[\\.][ ]{0,1}";
            else 
                result += charArray[i] + "[ ]{0,1}";
        }
        return str + "  regex:" + result;
    }
    
    private static List<String> readFile(String path){
        List<String> list =  new ArrayList<>(16);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line != null && line.length() != 0)
                    list.add(line);
            }
            return list;
        } catch (Exception e) {
          return list;
        }
    }
    
    private static void outFile(List<String> result, String outPath){
        String outStr = "";
        for (String str : result) {
            outStr += str + "\r\n";
        }
        
        String path = outPath;
        File file = new File(path);
        try {
            if (!file.exists()) 
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(outStr.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



