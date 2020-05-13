package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.product.ENProductTypeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.product.ZHProductTypeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年5月21日 下午1:36:59
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 抽取短语中关于产品类型的ner
 **/
public class NERProductTypeExtract extends NERProcessing<String>{
    
    private List<MatchingDataHandle> handles = new ArrayList<>(3);
    
    @Override
    public void initMatchingDataHandle(){
        handles.add(new ZHProductTypeMatchingDataHandle());
        handles.add(new ENProductTypeMatchingDataHandle());
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        initMatchingDataHandle();
        List<NERResultEntity> val = new ArrayList<>(8);
        for (MatchingDataHandle handle : handles) {
            List<NERResultEntity> matchingResult = handle.matchingResult(query);
            query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
            val.addAll(matchingResult);
        }
        // 根据抽取到第几类别排序
        List<NERResultEntity> collect = val.stream()
                .sorted((n1, n2) -> ((int)n2.getCondifence()) - ((int)n1.getCondifence()))
                .collect(Collectors.toList());
        
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("pcproduct");
        String key = property !=null ? property.toString() : "pcproduct";
        nerExtractResult.put(key, collect);
        
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        return true;
    }
    
}



