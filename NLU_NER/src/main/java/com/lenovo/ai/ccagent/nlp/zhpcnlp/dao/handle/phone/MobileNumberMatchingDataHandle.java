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
 * @createDate :2019年6月5日 上午10:08:01
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 移动电话号码抽取
 **/
public class MobileNumberMatchingDataHandle implements MatchingDataHandle {

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
        String snRegex = "((\\+86|\\+86\\-)|(86|86\\-)|(0086|0086\\-))?1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}";
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
        MobileNumberMatchingDataHandle mdh = new MobileNumberMatchingDataHandle();
        String query = "手机号码是+86-18348877932，182103 69476 ";
        System.out.println(JSONArray.fromObject(mdh.matchingResult(query)));
    }
}



