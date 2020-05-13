package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.software;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERConstants;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NumberUtils;

/**
 * @author :zhangchen
 * @createDate :2019年5月24日 下午1:44:01
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 以.exe结尾的   软件匹配数据   处理
 **/
public class EXESoftwareMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        if (str.endsWith(".exe")) str = str.substring(0, str.lastIndexOf(".exe"));
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4e00-\\u9fa5]");
        String[] arr = pattern.split(str);
        String result = "";
        if (StringUtils.isNotBlank(arr[0])) 
            result += arr[0].toLowerCase() + NERConstants.CONFIG_DATA_SPLIT_SYMBOL;
        for (int i = 0; i < arr.length; i ++) {
            Pattern isDigit = Pattern.compile("^[-\\+]?[\\d]*$");
            String s = arr[i];
            // 是否全是数字，如果全是数字就抛弃 ，（不包含第一个词）
            if (!isDigit.matcher(s).find() && s.length() > 1 && i > 0) 
                result += s.toLowerCase() + NERConstants.CONFIG_DATA_SPLIT_SYMBOL;
        }

        return result;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        List<NERResultEntity> result = new ArrayList<>();
        List<String> exeQuery = new ArrayList<>(8); 
        while (query.contains(".exe")) {
            int indexOf = query.indexOf(".exe");
            exeQuery.add(query.substring(0, indexOf));
            query = query.substring(indexOf + ".exe".length());
        }
        if (exeQuery.size() > 0) {
            Map<String, List<String>> exeSoftwareNer = NERDataCache.SOFTWARE_NER.get("exe");
            for (int i = 0; i < exeQuery.size(); i ++) {
                NERResultEntity nerResultEntity = nerNameMatching(exeSoftwareNer, exeQuery.get(i));
                if (nerResultEntity != null) result.add(nerResultEntity);
            }
        }
        return result;
    }

    private NERResultEntity nerNameMatching(Map<String, List<String>> exeSoftwareNer, String exe){
        List<String> softwareNerEntitys = new ArrayList<>(16);
        for (String key : exeSoftwareNer.keySet()) {
            if (exe.contains(key)) softwareNerEntitys.addAll(exeSoftwareNer.get(key));
        }
        
        // 排序 先匹配 最长的ner
        Collections.sort(softwareNerEntitys, (s1, s2) -> s2.length() - s1.length());

        for (String nerEntity : softwareNerEntitys) {
            String matchingResult = isMatching(nerEntity, exe);
            if (matchingResult != "") {
                NERResultEntity ner = new NERResultEntity();
                ner.setName(matchingResult);
                return ner;
            }
        }
        
        return null;
    }
    
    private String isMatching(String nerEntity, String exe){
        String[] nerEntityWords = nerEntity.split(NERConstants.CONFIG_DATA_SPLIT_SYMBOL);
        String result = exe;
        int indexOf = 0;
        for (int i = 0; i < nerEntityWords.length; i ++) {
            int words2IndexOf = result.indexOf(nerEntityWords[i]);
            if (words2IndexOf < indexOf) {
                // 短语中不包含这个单词，那就看看这个单词是不是有60%以上的全是数字，如果是那么就有可能是版本号，那么这个单词的匹配就容错
                if (!isVersionNumber(nerEntityWords[i]))  return ""; 
            }
            // 如果最后一个词汇匹配，就不用在截取了
            if (i != (nerEntityWords.length - 1)) {
                int subIndex = words2IndexOf + nerEntityWords[i].length();
                result = result.substring(Math.min(subIndex, result.length()));
            }
        }
        return exe.substring(exe.indexOf(nerEntityWords[0])) + ".exe";
    }
    
    private boolean isVersionNumber(String words){
        char[] charArray = words.toCharArray();
        int count = 0;
        for (int i = 0; i < charArray.length; i ++) {
            if (Character.isDigit(charArray[i])) count++;
        }
        double formatDouble = NumberUtils.formatDouble((double) count / (double) charArray.length, 1);
       
        return formatDouble >= 0.6;
    }
}



