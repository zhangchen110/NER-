package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;


/**
 * @author :zhangchen
 * @createDate :2019年9月17日 下午3:29:55
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 计算机硬件抽取规则加载
 **/
public class ComputerHardwareInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> readFile = FileIOUtils.readFile(path + "computer-hardware.txt");
        Map<String, String> map = new HashMap<>(8);
        for (String rf : readFile) {
            int indexOf = rf.indexOf("=");
            String key = rf.substring(0, indexOf);
            String val = rf.substring(indexOf + 1);
            map.put(key, val);
        }
        
        // 根据key排序
        List<String> collect = map.keySet().stream()
                    .sorted((k1, k2) -> k2.length() - k1.length())
                    .collect(Collectors.toList()); 
        collect.forEach(key -> NERDataCache.COMPUTER_HARDWARE_NER.put(key, map.get(key)));
    }

}



