package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.black_code;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

/**
 * @author :zhangchen
 * @createDate :2019年7月18日 上午10:17:37
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 黑屏错误代码  配置文件数据处理和抽取匹配
 **/
public class BlackCodeMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        String blackCode = str.toLowerCase();
        String[] split = blackCode.split(":");
        if (split.length >= 2 && split[0].contains("error")) {
            blackCode = blackCode.replaceAll("[^a-z0-9\\u4e00-\\u9fa5]", ".{0,1}");
            blackCode += "|" + split[0].replaceAll("[^a-z0-9\\u4e00-\\u9fa5]", ".{0,1}");
            blackCode += "|" + split[1].replaceAll("[^a-z0-9\\u4e00-\\u9fa5]", ".{0,1}");
            blackCode += "|" + split[0].replaceAll("[^0-9]", ""); 
        } else {
            blackCode = blackCode.replaceAll("[^a-z0-9\\u4e00-\\u9fa5]", ".{0,1}");
        }
           
        return blackCode;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        List<NERResultEntity> resultList = new ArrayList<>(8);
        List<String> blackErrorCodeRegex = NERDataCache.BLACK_ERROR_CODE;
        
        for (String regex : blackErrorCodeRegex) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(query);
            while (matcher.find()) {
                NERResultEntity entity = new NERResultEntity();
                entity.setName(matcher.group());
                resultList.add(entity);
            }
        }
        
        return resultList;
    }

    
    public static void main(String[] args) {
        BlackCodeMatchingDataHandle bcmd = new BlackCodeMatchingDataHandle();
        System.out.println(bcmd.dataHandle("Error 1962:No operating syste  found"));
        
        String query = "error 1962:No operating syste  found"
                .toLowerCase();
        Pattern pattern = Pattern.compile("error.{0,1}1962.{0,1}no.{0,1}operating.{0,1}syste.{0,1}.{0,1}found|error.{0,1}1962|no.{0,1}operating.{0,1}syste.{0,1}.{0,1}found|1962");
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()) {
            NERResultEntity entity = new NERResultEntity();
            entity.setName(matcher.group());
            System.out.println(matcher.group());
        }
        
    }
}



