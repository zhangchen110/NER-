package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.bsod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

/**
 * @author :zhangchen
 * @createDate :2019年5月30日 下午3:19:02
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 蓝屏错误码抽取
 **/
public class BsodErrorCodeMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        char[] arr = str.toCharArray();
        String regex = "";
        for (char c : arr) {
            if ((c >= 0x4e00) && (c <= 0x9fbb))
                regex += c + "?[ ]{0,1}";
            else
                regex += c + "[ ]{0,1}";
        }
        return regex.toLowerCase();
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        // 将query中的所有o O 都转换成0(因为蓝屏错误码没有o，并且容易将0输入成o)
        query = query.replaceAll("[oO]", "0");
        List<NERResultEntity> resultList = new ArrayList<>(8);
        List<String> bsodErrorCodeList = NERDataCache.BSOD_ERROR_CODE;
        for (String regex : bsodErrorCodeList) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                String name = matcher.group(0);
                NERResultEntity nerEntity = new NERResultEntity();
                nerEntity.setName(name);
                query = query.replace(name, "");
                resultList.add(nerEntity);
            }
        };
        
        return resultList;
    }

}



