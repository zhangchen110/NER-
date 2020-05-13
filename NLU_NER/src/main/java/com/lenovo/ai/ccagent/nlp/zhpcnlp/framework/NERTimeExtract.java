package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.time.TimeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年6月6日 下午5:51:23
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * ner中时间抽取
 **/
public class NERTimeExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        MatchingDataHandle mdh = new TimeMatchingDataHandle();
        List<NERResultEntity> matchingResult = mdh.matchingResult(query);
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("time");
        String key = property !=null ? property.toString() : "time";
        nerExtractResult.put(key, matchingResult);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        // TODO Auto-generated method stub
        return true;
    }

}



