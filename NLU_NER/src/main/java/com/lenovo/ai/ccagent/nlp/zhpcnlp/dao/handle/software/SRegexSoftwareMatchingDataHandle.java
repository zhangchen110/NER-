package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

/**
 * @author :zhangchen
 * @createDate :2019年7月2日 下午2:58:00
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 特殊正则匹配
 **/
public class SRegexSoftwareMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        return null;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        List<NERResultEntity> nerResultList = new ArrayList<>(8);
        List<String> softwareNerRegex = NERDataCache.SOFTWARE_NER_REGEX;
        for (String regex : softwareNerRegex) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                String group = matcher.group(1);
                NERResultEntity ner = new NERResultEntity();
                ner.setName(group);
                nerResultList.add(ner);
            }
        }
        return nerResultList;
    }

}



