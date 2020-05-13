package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.product_type.ProductType_TypeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月3日 下午5:26:46
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 产品类别抽取 比如笔记本，台式机
 **/
public class NERProductType_TypeExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        // TODO Auto-generated method stub
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        MatchingDataHandle mdh = new ProductType_TypeMatchingDataHandle();
        List<NERResultEntity> matchingResult = mdh.matchingResult(query);
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("product_type");
        String key = property !=null ? property.toString() : "product_type";
        nerExtractResult.put(key, matchingResult);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        return true;
    }

}



