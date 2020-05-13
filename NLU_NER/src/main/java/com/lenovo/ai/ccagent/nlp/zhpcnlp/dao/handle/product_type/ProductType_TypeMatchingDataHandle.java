package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.product_type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

/**
 * @author :zhangchen
 * @createDate :2019年7月3日 下午5:20:33
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class ProductType_TypeMatchingDataHandle implements MatchingDataHandle{

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
        Set<String> matchingSet = new HashSet<>();
        for (String regex : NERDataCache.PRODUCT_TYPE_TYPE_REGEX) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(query);
            while (matcher.find()) {
                String name = matcher.group();
                if (StringUtils.isBlank(name)) continue;
                matchingSet.add(name);
                query = query.replace(name, "");
            }
        }
       
        matchingSet.forEach(s -> {
            NERResultEntity nerEntity = new NERResultEntity();
            nerEntity.setName(s);
            result.add(nerEntity);
        });
        
        
        return result;
    }

}



