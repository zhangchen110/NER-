package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author :zhangchen
 * @createDate :2019年5月28日 下午7:30:51
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 英文 产品类型匹配数据处理
 **/
public class ENProductTypeMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
       return null;
    }
    
    
    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        // 去除query数字与数字之间的空格
        query = NERPublicUtils.removeNumberBetweenBlank(query);
        
        List<NERResultEntity> resultList = new ArrayList<>(8);
        JSONObject enProductType = NERDataCache.PRODUCT_TYPE_NER.get("enProductType");
        
        
        for (Object oneKey : enProductType.keySet()) {
                JSONObject oneVal = JSONObject.fromObject(enProductType.get(oneKey));
                for (Object twoKey : oneVal.keySet()) {
                    
                    if (query.contains(oneKey.toString().toLowerCase())) {
                        NERResultEntity nerEntity = new NERResultEntity();
                        nerEntity.setName(oneKey.toString());
                        nerEntity.setCondifence(1.0);
                        resultList.add(nerEntity);
                    }
                    
                    // 生成正则
                    String[] split = twoKey.toString().split("系列");
                    String twoKeyRegs = "";
                    if (split.length >= 2) {
                        twoKeyRegs = split[0] + "系?列?" + "(" + split[1] + ")?";
                    } else {
                        twoKeyRegs = split[0]  + "系?列?";
                    }
                    twoKeyRegs = twoKeyRegs.toLowerCase(); 
                    Pattern pattern = Pattern.compile(twoKeyRegs);
                    Matcher matcher = pattern.matcher(query);
                    if (matcher.find()) {
                        if (query.contains(oneKey.toString().toLowerCase()) && 
                                !twoKey.toString().toLowerCase().contains((oneKey.toString().toLowerCase()))) {
                            NERResultEntity nerEntity = new NERResultEntity();
                            nerEntity.setName(oneKey + " " + matcher.group(0));
                            nerEntity.setCondifence(2.0);
                            resultList.add(nerEntity);
                        } else  {
                            NERResultEntity nerEntity = new NERResultEntity();
                            nerEntity.setName(matcher.group(0));
                            nerEntity.setCondifence(2.0);
                            resultList.add(nerEntity);
                         }
                    }
                    
                    JSONArray twoVal = JSONArray.fromObject(oneVal.get(twoKey));
                    List<String> regexResult = convertRegex(query, twoVal);
                    if (!regexResult.isEmpty()) {
                        regexResult.forEach(n -> {
                            NERResultEntity nerEntity = new NERResultEntity();
                            nerEntity.setName(n);
                            nerEntity.setCondifence(3.0);
                            resultList.add(nerEntity);
                        });
                    } 
                }
        }
        
        return resultList;
    }
    
    
    /**
     * 将产品实体转换成正则抽取
     * @param zhProductTypeMap
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<String> convertRegex(String query, JSONArray twoVal){
        List<String> matchingResult = new ArrayList<>(8);
        Collections.sort(twoVal, (s1, s2) -> s2.toString().length() - s1.toString().length());
        for (Object e : twoVal) {
                 String regex = e.toString().replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", ".{0,2}");
                 // 生成正则
                 String regexMatching = threeRegexMatching(query, regex.toLowerCase());
                 if (StringUtils.isNotBlank(regexMatching)) {
                       query = query.replace(regexMatching, "");
                        matchingResult.add(regexMatching);
                }
          }
          
        
        return matchingResult;
    }
    
    private String threeRegexMatching(String query, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            String group = matcher.group(0); 
            
            // 最后一位是否需要匹配括号
            String matchingBrackets = NERPublicUtils.
                    matchingBrackets(query.substring(query.indexOf(group) + group.length()));
            group += matchingBrackets;
            
            return group;
        }
        return "";
    }

}



