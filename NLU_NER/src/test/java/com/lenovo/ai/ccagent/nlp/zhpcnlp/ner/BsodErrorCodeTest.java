package com.lenovo.ai.ccagent.nlp.zhpcnlp.ner;

import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月17日 下午6:14:07
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class BsodErrorCodeTest{

    public static void main(String[] args) {
        List<String> readFile = FileIOUtils.readFile("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\dictionary\\bsod-error-code.txt");
        
        List<String> readFile2 = FileIOUtils.readFile("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\test\\java\\com\\lenovo\\ai\\ccagent\\nlp\\zhpcnlp\\ner\\bsod-error-code-test.txt");
   
        readFile2.forEach(r -> {
            if (!readFile.contains(r)) 
                System.out.println(r);
        });
    }
}



