package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.sn.SNMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年6月4日 下午3:34:18
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * sn序列号抽取
 **/
public class NERSerialNumberExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
                 
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        List<NERResultEntity> val = new ArrayList<>(8);
        
        SNMatchingDataHandle snMatching = new SNMatchingDataHandle();
        List<NERResultEntity> matchingResult = snMatching.matchingResult(query);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        
        val.addAll(matchingResult);
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("sn");
        String key = property !=null ? property.toString() : "sn";
        nerExtractResult.put(key, val);
        
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        // TODO Auto-generated method stub
        return true;
    }

}



