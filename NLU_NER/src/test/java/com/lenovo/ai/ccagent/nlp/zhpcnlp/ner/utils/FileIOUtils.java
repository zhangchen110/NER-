package com.lenovo.ai.ccagent.nlp.zhpcnlp.ner.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhangchen
 * @createDate :2019年5月24日 下午2:05:26
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class FileIOUtils {

    public static List<String> readFile(String path){
        List<String> list =  new ArrayList<>(16);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line != null && line.length() != 0)
                    list.add(line);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



