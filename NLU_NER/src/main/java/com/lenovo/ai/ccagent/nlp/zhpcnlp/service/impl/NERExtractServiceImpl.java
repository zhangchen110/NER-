package com.lenovo.ai.ccagent.nlp.zhpcnlp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigInterceptNERNameLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERAddressExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERBlackErrorCodeExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERBrandExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERBsodErrorCodeExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERComputerHardwareExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NEREmailExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERMatchingRegexExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NEROperatingSystemExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERPhoneNumberExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERProcessing;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERProductTypeExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERProductType_TypeExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERSerialNumberExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERShortcutKeyExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERSoftwareExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.NERTimeExtract;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.service.NERExtractService;

import net.sf.json.JSONObject;

/**
 * @author :zhangchen
 * @createDate :2019年5月29日 上午10:24:23
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
@Service
public class NERExtractServiceImpl implements NERExtractService{

    @Override
    public String nerExtract(String query) {
        String handleQuery = handleInput(query);
        // 初始化执行链
        List<NERProcessing<String>> initHandleChain = initHandleChain();
        // 将所有的执行工作链连接起来
        NERProcessing<String> chain = initHandleChain.get(0);
        for (int i = 1; i < initHandleChain.size(); i++) {
            chain = chain.setSuccessor(initHandleChain.get(i));
        }
        
        Map<String, List<NERResultEntity>> nerExtractResult = new HashMap<>(8);
        
        initHandleChain.get(0).handle(handleQuery, nerExtractResult);
        
        Map<String, Object> result = buildReturnMap();
        result.put("nerResult", nerExtractResult);
        result.put("query", query);
        result.put("allEntity", isCompletelyNER(nerExtractResult, query) ? "yes" : "no");
        result.put("newQuery", query);
        return JSONObject.fromObject(result).toString();
    }
    
    private String handleInput(String query){
        query = query.toLowerCase();
        String regex = "[,，。！!？? ]";
        Pattern pattern = Pattern.compile(regex);
        String[] splitQuery = pattern.split(query);
        String joinQuery = Stream.of(splitQuery).reduce("", (s1, s2) -> {
            if (StringUtils.isNotBlank(s2)) return s1 += " " + s2;
            return s1;
        });
        return joinQuery;
    }
    
    private List<NERProcessing<String>> initHandleChain(){
        // 注意顺序，因为执行链会按着你的添加顺序执行
        List<NERProcessing<String>> nerProcessingList = new ArrayList<>(8);
        nerProcessingList.add(new NERComputerHardwareExtract());
        nerProcessingList.add(new NERBsodErrorCodeExtract());
        nerProcessingList.add(new NERBlackErrorCodeExtract());
        nerProcessingList.add(new NEREmailExtract());
        nerProcessingList.add(new NERProductTypeExtract());
        nerProcessingList.add(new NERAddressExtract());
        nerProcessingList.add(new NERSoftwareExtract());
        nerProcessingList.add(new NEROperatingSystemExtract());
        nerProcessingList.add(new NERTimeExtract());
        nerProcessingList.add(new NERProductType_TypeExtract());
        nerProcessingList.add(new NERShortcutKeyExtract());
        nerProcessingList.add(new NERBrandExtract());
        nerProcessingList.add(new NERSerialNumberExtract());
        nerProcessingList.add(new NERPhoneNumberExtract());
        nerProcessingList.add(new NERMatchingRegexExtract());
        
        return nerProcessingList;
    }
    
    
    private Map<String, Object> buildReturnMap(){
        Map<String, Object> result = new HashMap<>(8);
        result.put("uniformResult", new ArrayList<Object>());
        return result;
    }
    
    /**
     * 将query中抽取到的ner去除，生成新的字符串
     * @return
     */
    private String cancelMatchingNER(Map<String, List<NERResultEntity>> er, String query){
        String lowerQuery = query.toLowerCase();
        for (String key : er.keySet()) {
            if (er.get(key).isEmpty()) continue;
            for (NERResultEntity ne : er.get(key)) {
                String name = ne.getName();
                if (StringUtils.isNotBlank(name)) {
                    lowerQuery = lowerQuery.replace(name, "");
                    // 特殊处理地址
                    if (key.equals(ConfigNERNameKeyLoad.NER_NAME_KEYS.getProperty("address"))) {
                        String[] split = name.split(","); 
                        for (String n : split) {
                            lowerQuery = lowerQuery.replace(n, "");
                        }
                    }
                } 
            }
        }
       
        return lowerQuery;
        
    }
    
    /**
     * 表示用户整句是否都是需要拦截的ner
     * @param er
     * @param query
     * @return
     */
    private boolean isCompletelyNER(Map<String, List<NERResultEntity>> er, String query){
        String lowerQuery = query.toLowerCase();
        List<String> notBlankKeys = new ArrayList<>(8);
        for (String key : er.keySet()) {
            if (er.get(key).isEmpty()) continue;
            // 将抽到不是空的ner的key抽取出来
            notBlankKeys.add(key);
            // 判断抽到的ner是否是有要放过的
            if (!ConfigInterceptNERNameLoad.INTERCEPT_NER_NAME.contains(key)) return false;
        }
       
        for (String key : notBlankKeys) {
            for (NERResultEntity ne : er.get(key)) {
                String name = ne.getName();
                if (StringUtils.isNotBlank(name)) {
                    lowerQuery = lowerQuery.replace(name, "");
                    // 特殊处理地址
                    if (key.equals(ConfigNERNameKeyLoad.NER_NAME_KEYS.getProperty("address"))) {
                        String[] split = name.split(","); 
                        for (String n : split) {
                            lowerQuery = lowerQuery.replace(n, "");
                        }
                    }
                } 
            }
        }
        
        // 格式化去除数字，字母，汉字外的其他字符
        lowerQuery = lowerQuery.replaceAll("[^0-9a-z\\u4e00-\\u9fa5%]", "");
        if (StringUtils.isNotBlank(lowerQuery)) {
            return false;
        }
        
        return true;
    }
}



