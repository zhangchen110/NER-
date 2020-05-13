package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle;

import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;

/**
 * @author :zhangchen
 * @createDate :2019年5月24日 下午1:36:20
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 实体识别数据   匹配处理接口
 **/
public interface MatchingDataHandle {

    /**
     * 实体识别数据处理
     * @param str 实体识别数据
     * @return
     */
    String dataHandle(String str);
    
    /**
     * 抽取短语中匹配的ner数据
     * @param query 短语
     * @return
     */
    List<NERResultEntity> matchingResult(String query);
}



