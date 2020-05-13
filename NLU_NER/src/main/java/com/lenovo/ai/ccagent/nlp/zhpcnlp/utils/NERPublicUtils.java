package com.lenovo.ai.ccagent.nlp.zhpcnlp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;


/**
 * @author :zhangchen
 * @createDate :2019年5月29日 上午9:19:29
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 
 * ner公共抽取工具类
 **/
public class NERPublicUtils {

    
    public static String dislodgeMatchingNER(String query, List<NERResultEntity> res){
        String result = query;
//        Collections.sort(res, (n1, n2) -> ((int)n2.getCondifence()) - ((int)n1.getCondifence()));
//        for (NERResultEntity ner : res) {
//           result = result.replace(ner.getName(), "");
//        }
        return result;
    }
    
    /**
     * 匹配最后一位是否需要在匹配一个)}]）】
     * @param query
     * @return
     */
    public static String matchingBrackets(String subQuery){
        String result = "";
        if (subQuery.length() > 0) {
            char charAt = subQuery.charAt(0);
            if (charAt == ']' 
                    || charAt == '}' 
                    || charAt == ')'
                    || charAt == '）'
                    || charAt == '】') {
                result += charAt;
            }
        }
        
        return result;
    }
    
    /**
     * 去除数字之间的空格
     * @param str
     * @return
     */
    public static String removeNumberBetweenBlank(String str){
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ' && i > 0 && (i + 1) < str.length()) {
                char previous = str.charAt(i - 1);
                char after = str.charAt(i + 1);
                if (!Character.isDigit(previous) || !Character.isDigit(after))
                    result += c;
                    
            } else {
                result += c;
            }
        }
        return result;
    }
    
    /**
     * 将字符串转换成字符与字符之间可以包容空格的正则
     * 笔记本
     * 转换成
     * 笔[ ]{0,1}记[ ]{0,1}本
     * @param str
     * @return
     */
    public static String convertIntervalBlankRegex(String str){
        char[] charArray = str.toCharArray();
        String[] arr = new String[charArray.length];
        for (int i = 0; i < charArray.length ; i++) {
            arr[i] = charArray[i] + "";
        }
        return StringUtils.join(arr, "[ ]{0,1}");
    }
    
    /**
     * 正则匹配抽取
     * @param query 用户输入case
     * @param regexList 正则列表
     * @return 抽取出来的ner
     */
    public static List<NERResultEntity> regexMatchingResult(String query, List<String> regexList) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();

        List<NERResultEntity> result = new ArrayList<>(8);
        for (String regex : regexList) {
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
                    }
                }
                
            }
        }
       
        return result;
    }
    
    /**
     * 获取当前ner的type类型的名称
     * @param key 
     * @return
     */
    public static String getNERNameKey(String key){
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get(key);
        String typeName = property !=null ? property.toString() : key;
        return typeName;
    }
}



