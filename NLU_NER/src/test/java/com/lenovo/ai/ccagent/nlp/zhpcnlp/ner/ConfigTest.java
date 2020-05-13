package com.lenovo.ai.ccagent.nlp.zhpcnlp.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.sf.json.JSONArray;


public class ConfigTest {

    
    public static void main(String[] args) {

//        List<String> list =
//                readFile("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\software-list-zh.txt");
//        List<String> collect = list.stream()
//            .distinct()
//            .filter(s -> !isContainZH(s) && !s.toLowerCase().endsWith(".exe"))
//            .collect(Collectors.toList());
//        outFile(collect, "E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\test1.txt");
       
        removeDuplicate();  
    }
    
    private static boolean isContainZH(String s){
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(s);
        List<String> list = new ArrayList<>(8);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        String str = "";
        String zh = list.stream().reduce(str, (s1, s2) -> s1 += s2);
        double proportion = (double)(zh.length()) / (double)(s.length());
    
        if (proportion > 0.1) return true;
        
        return false;
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
    
    static final Pattern END_PUNC_REGEX = Pattern.compile("[^\\w\\d\\u4e00-\\u9fa5]{1,}$");
    
    static final Pattern BEGIND_PUNC_REGEX = Pattern.compile("^[^\\w\\d\\u4e00-\\u9fa5]{1,}");
   
    public static String replacePuncAroundWord(String line) {
        String[] words = line.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < words.length; i++) {
            Matcher m = BEGIND_PUNC_REGEX.matcher(words[i]);
            words[i] = m.replaceAll("");
            m = END_PUNC_REGEX.matcher(words[i]);
            words[i] = m.replaceAll("");
            sb.append(words[i] + " ");
        }
        return sb.toString().trim();
    }
    
    
    public static void removeDuplicate(){
        try {
            BufferedReader br1 = new BufferedReader(
                    new FileReader("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\bsod-error-code.txt")
                    );
            List<String> list = new ArrayList<>(8);
            String line = "";
            while ((line = br1.readLine()) != null)
                list.add(line);
            br1.close();
            
            BufferedReader br2 = new BufferedReader(
                    new FileReader("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\test.txt")
                    );
            while ((line = br2.readLine()) != null) {
                if (!list.contains(line))
                    System.out.println(line);
            }
                
            br2.close();
            
           
        } catch (Exception e) {
        }
       
    }
    
}



