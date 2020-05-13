package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.shortcut_key;

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
 * @createDate :2019年7月3日 下午5:35:02
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class ShortcutKeyMatchingDataHandle implements MatchingDataHandle{

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
        for (String regex : NERDataCache.SHORTCUT_KEY_REGEX) {
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
                        result.add(nerEntity);
                        break;
                    }
                }
                
            }
        }
       
        return result;
    }

}



