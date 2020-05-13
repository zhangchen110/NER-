package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.email;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;

import net.sf.json.JSONArray;

/**
 * @author :zhangchen
 * @createDate :2019年6月5日 上午11:27:16
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 电子邮箱抽取算法实现
 **/
public class EmailMatchingDataHandle implements MatchingDataHandle{

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
        String[] words = query.split(" ");
        // 匹配email的正则
        String snRegex = "[a-z0-9A-Z]+[-|a-z0-9A-Z\\._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+(cn|com|cc|tv|biz|xyz|net|top|tech|org|gov|edu|ink|red|mil|pub)$";
        Pattern pattern = Pattern.compile(snRegex);
        for (String w : words) {
            if (StringUtils.isBlank(w)) continue;
            Matcher matcher = pattern.matcher(w);
            if (matcher.find()) {
                NERResultEntity nerEntity = new NERResultEntity();
                nerEntity.setName(matcher.group(0));
                result.add(nerEntity);
            }
        }
        return result;
    }
    
    
    public static void main(String[] args) {
        EmailMatchingDataHandle mdh = new EmailMatchingDataHandle();
        String query = "我的邮箱是：1107773174@qq.com";
        System.out.println(JSONArray.fromObject(mdh.matchingResult(query)));
    }

}



