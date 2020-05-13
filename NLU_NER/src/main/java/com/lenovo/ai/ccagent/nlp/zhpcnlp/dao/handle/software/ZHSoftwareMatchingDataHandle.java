package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERConstants;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年5月24日 下午1:40:38
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 中文 软件匹配数据处理
 **/
public class ZHSoftwareMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        String newStr = discriminateZH(str);
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4e00-\\u9fa5\\.]");
        String[] arr = pattern.split(newStr);
        String result = "";
        if (StringUtils.isNotBlank(arr[0])) 
            result += arr[0].toLowerCase() + NERConstants.CONFIG_DATA_SPLIT_SYMBOL;
        for (int i = 0; i < arr.length; i ++) {
            String s = arr[i];
            if (s.length() > 0 && i > 0) {
                  result += s.toLowerCase() + NERConstants.CONFIG_DATA_SPLIT_SYMBOL;
            }
        }
        return result;
    }
    
    private String discriminateZH(String str){
        String newStr = "";
        // 区分汉字和非汉字的状态 0汉字，1非汉字
        int state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c >= 0x4e00) && (c <= 0x9fbb)) {
                if (state == 0) {
                    newStr += c;
                } else {
                    newStr += " " + c;
                    state = 0;
                }
            } else {
                if (state == 1) {
                    newStr += c;
                } else {
                    newStr += " " + c;
                    state = 1;
                }
            }
        }
        return newStr;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        List<NERResultEntity> result = new ArrayList<>();
        Map<String, List<String>> zhSoftwareNer = NERDataCache.SOFTWARE_NER.get("zh");
        
        List<String> softwareNerEntitys = new ArrayList<>(16);
        for (String key : zhSoftwareNer.keySet()) {
            if (query.contains(key)) softwareNerEntitys.addAll(zhSoftwareNer.get(key));
        }
        // 排序 先匹配 最长的ner
        Collections.sort(softwareNerEntitys, (s1, s2) -> s2.length() - s1.length());
        String queryCopy = query;
        for (String nerEntity : softwareNerEntitys) {
            String matchingResult = isMatching(nerEntity, queryCopy);
            if (matchingResult != "") {
                NERResultEntity ner = new NERResultEntity();
                ner.setName(matchingResult);
                result.add(ner);
                String[] mrs = matchingResult.split(" ");
                for (int i = 0; i < mrs.length; i++)
                   queryCopy = queryCopy.replace(mrs[i], "");
            }
        }
        return result;
    }
    
    
    private String isMatching(String nerEntity, String query) {
        String[] nerEntityWords = nerEntity.split(NERConstants.CONFIG_DATA_SPLIT_SYMBOL);
        // 先将第一个可以匹配到的words取出来
        String result = "";
        String subQuery = query;
        for (int i = 0; i < nerEntityWords.length; i ++) {
            
            int words2indexOf = subQuery.indexOf(nerEntityWords[i]);
            // 因为下一个单词要连接上一个单词，所以words2indexOf > 5  说明与上一个单词中间有两个以上的字符间隔，不允许；
            // 容错率只允许最多5个的字符间隔(要将第一个单词跳过)
            if (i == 0 && words2indexOf > -1) {
                result += nerEntityWords[i];
                subQuery = subQuery.substring(words2indexOf + nerEntityWords[i].length());
                continue;
            }
            if (words2indexOf > 5) return "";
            // words2indexOf < 0 说明query中没有匹配到，然后判断这个词汇是否是版本号，那么这个词汇就可以部分容错
            if (words2indexOf < 0) {
                String end = isMatchingVersionNumber(nerEntityWords[i], subQuery);
                if (end == "")
                    return "";
                else {
                    result += " " + end;
                    subQuery = subQuery.substring(subQuery.indexOf(end) + end.length());
                }
            } else {
                result += subQuery.substring(0, words2indexOf + nerEntityWords[i].length());
                subQuery = subQuery.substring(words2indexOf + nerEntityWords[i].length());
            }
        }
        
        result += NERPublicUtils.matchingBrackets(subQuery);
        
        return result;
    }
    
    
    private String isMatchingVersionNumber(String words, String query){
        words = discriminateDigit(words);
        words = words.replace(".", " . ");
        String[] wordsArray = words.split(" ");
        
        String subQuery = query.replace(" ", "");
        // 生成正则
        String regex = "";
        for (int i = 0; i < wordsArray.length; i ++) {
            if (wordsArray[i].matches("^[-\\+]?[a-zA-Z\\u4e00-\\u9fa5]*$")) {
                regex += wordsArray[i];
            } else if (wordsArray[i].equals(".")) {
                regex += "[\\.]";
            } else {
                regex += "[\\d]{" + wordsArray[i].length() + "," + (wordsArray[i].length() + 1) + "}";
            }
        }
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(subQuery);
        String result = "";
        if (matcher.find()) {
            String group = matcher.group(0);
            if (subQuery.indexOf(group) == 0)
                result = group;
        }
        
        return result;
    }
    
    private String discriminateDigit(String str){
        String newStr = "";
        // 区分字符和[数字.]的区别 0是字符，1是[数字.]
        int state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                if (state == 1) {
                    newStr += c;
                } else {
                    newStr += " " + c;
                    state = 1;
                }
            } else {
                if (state == 0) {
                    newStr += c;
                } else {
                    newStr += " " + c;
                    state = 0;
                }
            }
        }
        return newStr;
    }

}



