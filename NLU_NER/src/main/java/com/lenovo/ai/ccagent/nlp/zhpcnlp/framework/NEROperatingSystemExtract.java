package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.os.OperatingSystemMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年5月31日 下午2:21:48
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 操作系统抽取
 **/
public class NEROperatingSystemExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        // TODO Auto-generated method stub
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        List<NERResultEntity> val = new ArrayList<>(8);
        
        OperatingSystemMatchingDataHandle mdh = new OperatingSystemMatchingDataHandle();
        List<NERResultEntity> matchingResult = mdh.matchingResult(query);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        val.addAll(matchingResult);
        
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("os");
        String key = property !=null ? property.toString() : "os";
        nerExtractResult.put(key, val);
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        // TODO Auto-generated method stub
        return true;
    }
  
}



