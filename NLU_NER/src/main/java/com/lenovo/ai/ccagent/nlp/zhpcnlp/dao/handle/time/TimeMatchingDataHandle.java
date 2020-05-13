package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.time;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.TimeRegexInitLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

/**
 * @author :zhangchen
 * @createDate :2019年6月6日 下午5:53:06
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * ner时间抽取处理
 **/
public class TimeMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        List<NERResultEntity> result = new ArrayList<>(8);
        for (String regex : NERDataCache.TIME_REGEX) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(query);
            while (matcher.find()) {
                String name = matcher.group();
                if (StringUtils.isBlank(name)) continue;
                NERResultEntity nerEntity = new NERResultEntity();
                nerEntity.setName(name);
                result.add(nerEntity);
                query = query.replace(name, "");
            }
        }
       
        return result;
    }
    
    public static void main(String[] args) {
        TimeRegexInitLoad load = new TimeRegexInitLoad();
        load.init("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\dictionary\\");
        TimeMatchingDataHandle mdh = new TimeMatchingDataHandle();
        String query = "明年的今天就是6月10日";
        System.out.println(mdh.matchingResult(query));
    }

}



