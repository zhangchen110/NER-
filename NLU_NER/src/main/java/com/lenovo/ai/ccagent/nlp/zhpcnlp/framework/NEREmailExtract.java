package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.email.EmailMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年6月5日 上午11:24:21
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 电子邮箱抽取
 **/
public class NEREmailExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {}

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        MatchingDataHandle mdh = new EmailMatchingDataHandle();
        List<NERResultEntity> matchingResult = mdh.matchingResult(query);
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("email");
        String key = property !=null ? property.toString() : "email";
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



