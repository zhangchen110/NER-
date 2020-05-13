package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.phone.FixedNumberMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.phone.MobileNumberMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年6月4日 下午6:09:02
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 电话号码抽取
 **/
public class NERPhoneNumberExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        // 先抽取移动号码，在抽取固话号码
        MobileNumberMatchingDataHandle mn = new MobileNumberMatchingDataHandle();
        List<NERResultEntity> mnMatchingResult = mn.matchingResult(query);
        Object property1 = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("mobileNumber");
        String key1 = property1 != null ? property1.toString() : "mobileNumber";
        if (nerExtractResult.get(key1) == null) {
            nerExtractResult.put(key1, mnMatchingResult);
        } else {
            nerExtractResult.get(key1).addAll(mnMatchingResult);
        }
        
        query = NERPublicUtils.dislodgeMatchingNER(query, mnMatchingResult);
        
        FixedNumberMatchingDataHandle fn = new FixedNumberMatchingDataHandle();
        List<NERResultEntity> fnMatchingResult = fn.matchingResult(query);
        Object property2 = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("fixedNumber");
        String key2 = property2 != null ? property2.toString() : "fixedNumber";
        
        if (nerExtractResult.get(key2) == null) {
            nerExtractResult.put(key2, fnMatchingResult);
        } else {
            nerExtractResult.get(key2).addAll(fnMatchingResult);
        }
        query = NERPublicUtils.dislodgeMatchingNER(query, fnMatchingResult);
       
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        return true;
    }

}



