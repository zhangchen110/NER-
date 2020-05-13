package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software.ENSoftwareMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software.EXESoftwareMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software.ZHSoftwareMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERConstants;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2019年5月28日 下午4:28:16
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 软件ner初始化数据加载
 **/
public class SoftwareInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> sRegex = FileIOUtils.readFile(path + "software-list-s-regex.txt");
        sRegex.forEach(r -> NERDataCache.SOFTWARE_NER_REGEX.add(r.toLowerCase()));
        
        Map<String, List<String>> softwareEXE = 
                convertData(FileIOUtils.readFile(path + "software-list-exe.txt"),
                new EXESoftwareMatchingDataHandle());
        NERDataCache.SOFTWARE_NER.put("exe", softwareEXE);
        Map<String, List<String>> softwareZH = 
                convertData(FileIOUtils.readFile(path + "software-list-zh.txt"),
                new ZHSoftwareMatchingDataHandle());
        NERDataCache.SOFTWARE_NER.put("zh", softwareZH);
        Map<String, List<String>> softwareEN = 
                convertData(FileIOUtils.readFile(path + "software-list-en.txt"),
                new ENSoftwareMatchingDataHandle());
        NERDataCache.SOFTWARE_NER.put("en", softwareEN);
    }
    
    private Map<String, List<String>> convertData(List<String> list, MatchingDataHandle handle){
        Map<String, List<String>> converMap = new HashMap<>(16);
        list.stream().forEach(str -> {
            String handleData = handle.dataHandle(str);
            if (StringUtils.isNotBlank(handleData)) {
                if (handleData.endsWith(NERConstants.CONFIG_DATA_SPLIT_SYMBOL))
                    handleData = handleData.substring(0, handleData.length()-1);
                String key = handleData.split(NERConstants.CONFIG_DATA_SPLIT_SYMBOL)[0];
                List<String> val = converMap.get(key);
                if (val == null) {
                    val = new ArrayList<>(8);
                    converMap.put(key, val);
                }
                val.add(handleData);
            }
        });
        
        for (String key : converMap.keySet()) {
            converMap.put(key, converMap.get(key).stream()
                    .distinct()
                    .sorted((s1, s2) -> s2.length() - s1.length())
                    .collect(Collectors.toList()));
        }
        
        return converMap;
    }
    
}



