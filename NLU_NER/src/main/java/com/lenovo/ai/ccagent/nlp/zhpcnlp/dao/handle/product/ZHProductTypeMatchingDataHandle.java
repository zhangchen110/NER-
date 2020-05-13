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
 * @createDate :2019年5月28日 下午7:31:46
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 中文 产品类型匹配数据处理
 **/
public class ZHProductTypeMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        // 去除query数字与数字之间的空格
        query = NERPublicUtils.removeNumberBetweenBlank(query);
        
        List<NERResultEntity> resultList = new ArrayList<>(8);
        JSONObject zhProductTypeMap =
                NERDataCache.PRODUCT_TYPE_NER.get("zhProductType");
        
        for (Object oneKey : zhProductTypeMap.keySet()) {
            if (query.contains(oneKey.toString())) {
                JSONObject oneVal = JSONObject.fromObject(zhProductTypeMap.get(oneKey));
                for (Object twoKey : oneVal.keySet()) {
                    // 生成正则
                    String[] split = twoKey.toString().split("系列");
                    String twoKeyRegs = "";
                    if (split.length >= 2) {
                        twoKeyRegs = NERPublicUtils.convertIntervalBlankRegex(split[0]) + "系?列?" 
                                   + "([ ]{0,1}" + NERPublicUtils.convertIntervalBlankRegex(split[1]) + ")?";
                    } else {
                        twoKeyRegs = NERPublicUtils.convertIntervalBlankRegex(split[0])  + "系?列?";
                    }
                    twoKeyRegs = twoKeyRegs.toLowerCase(); 
                    Pattern pattern = Pattern.compile(twoKeyRegs);
                    Matcher matcher = pattern.matcher(query);
                    if (matcher.find()) {
                        JSONArray twoVal = JSONArray.fromObject(oneVal.get(twoKey));
                        List<String> regexResult = convertRegex(query, oneKey.toString(), twoKey.toString(), twoVal);
                        if (regexResult.isEmpty()) {
                            NERResultEntity nerEntity = new NERResultEntity();
                            nerEntity.setName(matcher.group(0));
                            nerEntity.setCondifence(2.0);
                            resultList.add(nerEntity);
                        } else {
                            regexResult.forEach(n -> {
                                NERResultEntity nerEntity = new NERResultEntity();
                                nerEntity.setName(n);
                                nerEntity.setCondifence(3.0);
                                resultList.add(nerEntity);
                            });
                        }
                    }
                }
                
                NERResultEntity nerEntity = new NERResultEntity();
                nerEntity.setName(oneKey.toString());
                nerEntity.setCondifence(1.0);
                resultList.add(nerEntity);
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
    private List<String> convertRegex(String query, String oneKey, String twoKey, JSONArray twoVal){
        List<String> matchingResult = new ArrayList<>(8);
        Collections.sort(twoVal, (s1, s2) -> s2.toString().length() - s1.toString().length());
        for (Object e : twoVal) {
                    // 生成正则
                    if (twoKey.contains(oneKey) && twoKey.contains("系列")) {
                        String series = twoKey.substring(0, twoKey.indexOf("系列")).replace(oneKey.toString(), "");
                        String regex = oneKey.toString() + ".{0,2}" + series;
                        String end = e.toString().replace(oneKey.toString() + series, "");
                        if (StringUtils.isNotBlank(end))
                            regex += ".{0,2}" + NERPublicUtils.convertIntervalBlankRegex(end);
                        String regexMatching = threeRegexMatching(query, regex.toLowerCase());
                        if (StringUtils.isNotBlank(regexMatching)) {
                                 query = query.replace(regexMatching, "");
                                 matchingResult.add(regexMatching);
                        }
                    } else {
                        String regexMatching = threeRegexMatching(query, e.toString().toLowerCase());
                        if (StringUtils.isNotBlank(regexMatching)) {
                             query = query.replace(regexMatching, "");
                             matchingResult.add(regexMatching);
                        }
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



