package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;

/**
 * @author :zhangchen
 * @createDate :2019年5月21日 上午10:03:03
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * ner 抽取接口
 **/
public interface NERExtract<T> {
    
    /**
     * 初始化责任链中的执行器
     */
    public void initMatchingDataHandle();
    

    /**
     * 短语抽取
     * @param query 短语
     */
    T extract(T query, Map<String, List<NERResultEntity>> nerExtractResult);
    
    /**
     * 判断是否要执行当前的处理链
     * @param query 短语
     * @return
     */
    boolean isExecute(T query);
}



