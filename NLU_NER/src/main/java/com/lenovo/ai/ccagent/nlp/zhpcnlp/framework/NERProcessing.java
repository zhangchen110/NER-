package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;

/**
 * @author :zhangchen
 * @createDate :2019年5月21日 上午10:08:55
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * ner链节点处理类
 **/
public abstract class NERProcessing<T> implements NERExtract<T>{

    private NERProcessing<T> successor;
    
    /**
     * 当前责任链节点储存了下一个责任链，那么将下一个责任链节点返回，准备继续连接其他的责任链节点
     * @param succ 链节点
     * @return 返回参数链节点
     */
    public NERProcessing<T> setSuccessor(NERProcessing<T> succ){
        this.successor = succ;
        return succ;
    }
    
    /**
     * ner处理链入口
     * @param query 短语
     * @return
     */
    public void handle(T query, 
            Map<String, List<NERResultEntity>> nerExtractResult){
        if (isExecute(query)) 
            query = extract(query, nerExtractResult);
        if (this.successor != null) 
            successor.handle(query, nerExtractResult);
    }
    
}



