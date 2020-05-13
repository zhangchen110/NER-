package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.NERInitLoad;

/**
 * @author :zhangchen
 * @createDate :2019年7月11日 下午4:14:44
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 加载每类ner的映射key
 **/
public class ConfigNERNameKeyLoad implements NERInitLoad{
    
    public final static Properties NER_NAME_KEYS = new Properties();

    @Override
    public void init(String path) {
        File file = new File(path + "config/ner-name-key.properties");
        try {
            NER_NAME_KEYS.load(new FileInputStream(file));
        } catch (Exception e) {
            
        }
        
    }

}



