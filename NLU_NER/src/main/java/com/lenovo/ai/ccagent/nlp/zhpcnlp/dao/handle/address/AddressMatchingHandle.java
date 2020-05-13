package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.address;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.bean.AddressBean;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

/**
 * @author :zhangchen
 * @createDate :2019年8月5日 下午6:20:49
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 省市区地址匹配抽取处理类
 **/
public class AddressMatchingHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        
         List<NERResultEntity> resultEntity = new ArrayList<>(1);
         String addressStr = "";
         
         // 首先匹配省，然后根据匹配到的省去匹配市，如果未匹配到省，哪就直接去匹配市
         // 如果有多个省市，那么目前只是抽取第一个出现的
         Map<String, List<AddressBean>> province = NERDataCache.ADDRESS_NER.get("province");
         Collection<List<AddressBean>> provinceValues = new ArrayList<>(8);
         if (province != null)
             provinceValues = province.values();
         Map<String, Object> provinceMap = extractAddress(provinceValues, query);
         Object pCode = provinceMap.get("code");
         
         
         Collection<List<AddressBean>> cityValues = new ArrayList<>(8);
         if (pCode != null) {
             cityValues.add(NERDataCache.ADDRESS_NER.get("city").get(pCode.toString()));
             addressStr += provinceMap.get("name") + ",";
         } else {
             Map<String, List<AddressBean>> city = NERDataCache.ADDRESS_NER.get("city");
             if (city != null)
                 cityValues = city.values();
         }
         Map<String, Object> cityMap = extractAddress(cityValues, query);
         Object cCode = cityMap.get("code");
         
         
         Collection<List<AddressBean>> countyValues = new ArrayList<>(8);
         if (cCode != null) {
             countyValues.add(NERDataCache.ADDRESS_NER.get("county").get(cCode.toString()));
             addressStr += cityMap.get("name") + ",";
         } else {
             Map<String, List<AddressBean>> county = NERDataCache.ADDRESS_NER.get("county");
             if (county != null)
                countyValues = county.values();
         }
         Map<String, Object> countyMap = extractAddress(countyValues, query);
        
         addressStr += countyMap.get("name") != null? countyMap.get("name").toString() : "";
         if (StringUtils.isNotBlank(addressStr)) {
             NERResultEntity entity = new NERResultEntity();
             if (addressStr.endsWith(",")) {
                 addressStr = addressStr.substring(0, addressStr.length() - 1);
             }
             entity.setName(addressStr);
             resultEntity.add(entity);
         }
         
        return resultEntity;
    }
    
    private Map<String, Object> extractAddress(Collection<List<AddressBean>> addressValues, String query){
        HashMap<String, Object> resultMap = new HashMap<>(1);
        for (List<AddressBean> pl : addressValues) {
            for (AddressBean p : pl) {
                String regex = p.getRegex();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(query);
                if (matcher.find()) {
                   resultMap.put("name", matcher.group(0));
//                    resultMap.put("name", regex.replaceAll("[^a-z\\u4e00-\\u9fa5]", ""));
                    resultMap.put("code", p.getCode());
                    return resultMap;
                }
            }
        }
        
        return resultMap;
    }

}



