package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.bsod.BsodErrorCodeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年5月30日 下午3:02:08
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 蓝屏错误码抽取
 **/
public class NERBsodErrorCodeExtract extends NERProcessing<String>{

    
    @Override
    public void initMatchingDataHandle() {
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        List<NERResultEntity> val = new ArrayList<>(8);
        
        MatchingDataHandle bsodErrorCodeMDH = new BsodErrorCodeMatchingDataHandle();
        List<NERResultEntity> matchingResult = bsodErrorCodeMDH.matchingResult(query);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        
        val.addAll(matchingResult);
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("bsodErrorCode");
        String key = property !=null ? property.toString() : "bsodErrorCode";
        nerExtractResult.put(key, val);
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        return true;
    }

}



