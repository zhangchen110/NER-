package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.AddressInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.BlackScreenErrorCode;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.BrandInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.BsodErrorCodeInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.ComputerHardwareInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.NERInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.NERRegexExtractInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.OperatingSystemInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.ProductTypeInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.ProductType_TypeInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.SNInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.ShortcutKeyInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.SoftwareInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.TimeRegexInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigInterceptNERNameLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;

/**
 * @author :zhangchen
 * @createDate :2019年5月23日 下午3:25:05
 * @Email :zhangchen17@lenovo.com
 * @Description :
 *  要匹配抽取的ner数据
 **/
@Component
public class NERExtractMatchingDataLoad {
    
    @Value("${NER_Dictionary_Path}")
    private String ner_Dictionary_Path;
    
    /**
     * 初始化数据
     */
    @PostConstruct
    public void init(){
        List<NERInitLoad> initNERInitLoader = initNERInitLoader();
        for (NERInitLoad loader : initNERInitLoader) {
            loader.init(ner_Dictionary_Path);
        }
    }
    
    private List<NERInitLoad> initNERInitLoader(){
        List<NERInitLoad> loader = new ArrayList<>(8);
        loader.add(new ConfigNERNameKeyLoad());
        loader.add(new ConfigInterceptNERNameLoad());
        
        loader.add(new BsodErrorCodeInitLoad());
        loader.add(new SoftwareInitLoad());
        loader.add(new ProductTypeInitLoad());
        loader.add(new OperatingSystemInitLoad());
        loader.add(new TimeRegexInitLoad());
        loader.add(new ProductType_TypeInitLoad());
        loader.add(new ShortcutKeyInitLoad());
        loader.add(new BrandInitLoad());
        loader.add(new BlackScreenErrorCode());
        loader.add(new AddressInitLoad());
        loader.add(new ComputerHardwareInitLoad());
        loader.add(new NERRegexExtractInitLoad());
        loader.add(new SNInitLoad());
        return loader;
    }
    
    
}



