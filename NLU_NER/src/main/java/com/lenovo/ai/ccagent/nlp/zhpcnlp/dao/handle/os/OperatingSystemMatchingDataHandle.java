package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.os;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;


/**
 * @author :zhangchen
 * @createDate :2019年5月31日 下午2:23:42
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class OperatingSystemMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        
        return str.split("regex:")[1];
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        List<NERResultEntity> resultList = new ArrayList<>(8);
        List<String> operatingSystemList = NERDataCache.OPERATING_SYSTEM;
        for (String regex : operatingSystemList) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(query);
            
            if (matcher.find()) {
                int groupCount = matcher.groupCount();
                String name = "";
                for (int i = 1; i <= groupCount; i++) {
                    name = matcher.group(i);
                    if (StringUtils.isNotBlank(name)) {
                        NERResultEntity nerEntity = new NERResultEntity();
                        nerEntity.setName(name);
                        query = query.replace(name, "");
                        resultList.add(nerEntity);
                    }
                }
                
            }
        }
        
        return resultList;
    }

    
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(w[ ]{0,1}i[ ]{0,1}n[ ]{0,1}d?[ ]{0,1}o?[ ]{0,1}w?[ ]{0,1}s?[ ]{0,1}1[ ]{0,1}[\\.][ ]{0,1}0)");
        Matcher matcher = pattern.matcher("windows 1.0");
        if (matcher.find()) {
            int groupCount = matcher.groupCount();
            String name = "";
            for (int i = 1; i <= groupCount; i++) {
                name = matcher.group(i);
                if (StringUtils.isNotBlank(name)) {
                   System.out.println(name);
                }
            }
            
        }
    }
    
}



