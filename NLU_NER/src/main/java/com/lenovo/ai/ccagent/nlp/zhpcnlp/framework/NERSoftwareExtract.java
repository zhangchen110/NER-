package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software.ENSoftwareMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software.EXESoftwareMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software.SRegexSoftwareMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software.ZHSoftwareMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;


/**
 * @author :zhangchen
 * @createDate :2019年5月21日 上午11:03:37
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 抽取短语中关于软件的ner
 **/
public class NERSoftwareExtract extends NERProcessing<String>{
    
    private List<MatchingDataHandle> handles = new ArrayList<>(3);
    
    @Override
    public void initMatchingDataHandle(){
        handles.add(new SRegexSoftwareMatchingDataHandle());
        handles.add(new EXESoftwareMatchingDataHandle());
        handles.add(new ZHSoftwareMatchingDataHandle());
        handles.add(new ENSoftwareMatchingDataHandle());
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        // 初始化 执行器
        initMatchingDataHandle();
        
        List<NERResultEntity> val = new ArrayList<>(8);
        for (MatchingDataHandle handle : handles) {
            List<NERResultEntity> matchingResult = handle.matchingResult(query);
            query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
            val.addAll(matchingResult);
        }
        
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("3rd_software");
        String key = property !=null ? property.toString() : "3rd_software";
        nerExtractResult.put(key, val);
        
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        return true;
    }
    
    
}



