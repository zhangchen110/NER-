package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.address.AddressMatchingHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年8月6日 上午11:16:02
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class NERAddressExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        AddressMatchingHandle matchingHandle = new AddressMatchingHandle();
        List<NERResultEntity> matchingResult = matchingHandle.matchingResult(query);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("address");
        String key = property !=null ? property.toString() : "address";
        nerExtractResult.put(key, matchingResult);
        
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        // TODO Auto-generated method stub
        return true;
    }

}



