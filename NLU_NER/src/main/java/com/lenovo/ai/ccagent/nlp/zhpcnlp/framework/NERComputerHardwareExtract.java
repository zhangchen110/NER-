package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.computer_hardware.ComputerHardwareDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;

/**
 * @author :zhangchen
 * @createDate :2019年9月17日 下午3:22:42
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 计算机硬件抽取
 **/
public class NERComputerHardwareExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        MatchingDataHandle computerHardwareDataHandle = new ComputerHardwareDataHandle();
        List<NERResultEntity> matchingResult = computerHardwareDataHandle.matchingResult(query);
        // 计算机硬件抽取不在去去除query中抽取到的ner词汇
        
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("computer_hardware");
        String key = property !=null ? property.toString() : "computer_hardware";
        nerExtractResult.put(key, matchingResult);
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        // TODO Auto-generated method stub
        return true;
    }

}



