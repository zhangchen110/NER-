package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.phone;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

import net.sf.json.JSONArray;

/**
 * @author :zhangchen
 * @createDate :2019年6月4日 下午6:11:37
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 固定号码(座机号码)抽取实现
 **/
public class FixedNumberMatchingDataHandle implements MatchingDataHandle {

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
        // 去除数字之间的空格
        query = NERPublicUtils.removeNumberBetweenBlank(query);
        // 匹配座机号码的正则
        String snRegex = "(0\\d{2,3}\\-)?([2-9]\\d{6,7})+(\\-\\d{1,6})?";
        Pattern pattern = Pattern.compile(snRegex);
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()) {
            String group = matcher.group();
            NERResultEntity nerEntity = new NERResultEntity();
            nerEntity.setName(group);
            result.add(nerEntity);
        }
        return result;
    }
    
    public static void main(String[] args) {
        FixedNumberMatchingDataHandle mdh = new FixedNumberMatchingDataHandle();
        String query = "张晨：0233-83488779-0833";
        System.out.println(JSONArray.fromObject(mdh.matchingResult(query)));
    }

}



