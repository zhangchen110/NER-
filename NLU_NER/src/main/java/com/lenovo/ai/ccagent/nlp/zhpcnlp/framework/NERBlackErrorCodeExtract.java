package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.black_code.BlackCodeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月18日 上午11:21:18
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 黑屏错误代码抽取
 **/
public class NERBlackErrorCodeExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        
        BlackCodeMatchingDataHandle handle = new BlackCodeMatchingDataHandle();
        List<NERResultEntity> matchingResult = handle.matchingResult(query);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("blackErrorCode");
        String key = property !=null ? property.toString() : "blackErrorCode";
        nerExtractResult.put(key, matchingResult);
        
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        return true;
    }

}



