package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config;

import java.util.ArrayList;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.NERInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2019年11月29日 下午5:24:16
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 
 * 要拦截的ner的name加载（比如整句query都是该ner的情况下需要拦截）
 **/
public class ConfigInterceptNERNameLoad implements NERInitLoad{

    public static final List<String> INTERCEPT_NER_NAME = new ArrayList<>(8);
    
    @Override
    public void init(String path) {
        List<String> interceptNERName = FileIOUtils.readFile(path + "config/intercept-ner-key.txt");
        INTERCEPT_NER_NAME.addAll(interceptNERName);
    }

}



