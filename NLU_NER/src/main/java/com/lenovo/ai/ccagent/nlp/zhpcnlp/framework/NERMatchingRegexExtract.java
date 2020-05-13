package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2020年2月13日 下午3:00:44
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 符合使用正则的抽取ner
 **/
public class NERMatchingRegexExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        // 杀毒软件
        List<NERResultEntity> sdMatchingResult = NERPublicUtils.regexMatchingResult(query, NERDataCache.SD_SOFTWARE_NER);
        nerExtractResult.put(NERPublicUtils.getNERNameKey("sd_software"), sdMatchingResult);
        // 蓝屏提示
        List<NERResultEntity> blueTipsMatchingResult = NERPublicUtils.regexMatchingResult(query, NERDataCache.BLUE_TIPS_NER);
        nerExtractResult.put(NERPublicUtils.getNERNameKey("blue_tips"), blueTipsMatchingResult);
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        // TODO Auto-generated method stub
        return true;
    }

}



