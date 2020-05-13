package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.computer_hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

/**
 * @author :zhangchen
 * @createDate :2019年9月17日 下午4:22:36
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 计算机硬件抽取处理
 **/
public class ComputerHardwareDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        List<NERResultEntity> resultList = new ArrayList<>(8);
        Map<String, String> computerHardwareNer = NERDataCache.COMPUTER_HARDWARE_NER;
        for (String key : computerHardwareNer.keySet()) {
            Pattern pattern = Pattern.compile(computerHardwareNer.get(key));
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                String name = matcher.group(1);
                NERResultEntity nerEntity = new NERResultEntity();
                nerEntity.setName(key);
                query = query.replace(name, "");
                resultList.add(nerEntity);
            }
        }
        return resultList;
    }

}



