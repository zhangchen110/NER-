package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.bean.AddressBean;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

import net.sf.json.JSONObject;

/**
 * @author :zhangchen
 * @createDate :2019年8月5日 下午6:17:00
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 地址(省市县)加载
 **/
public class AddressInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
          List<String> provinceList = FileIOUtils.readFile(path + "address/province.txt");
          NERDataCache.ADDRESS_NER.put("province", dataHandle(provinceList));
          List<String> cityList = FileIOUtils.readFile(path + "address/city.txt");
          NERDataCache.ADDRESS_NER.put("city", dataHandle(cityList));
          List<String> countyList = FileIOUtils.readFile(path + "address/county.txt");
          NERDataCache.ADDRESS_NER.put("county", dataHandle(countyList));
    }
    
    private Map<String, List<AddressBean>> dataHandle(List<String> addressList){
        Map<String, List<AddressBean>> valAddress = new HashMap<>(8);
        for (String address : addressList) {
            // 跳过注释的
            if (address.startsWith("#")) {
                System.out.println(address);
                continue;
            }
            String[] split = address.split(",");
            String endName = split[1].replace(split[2], "");
            String regex = split[2];
            if (StringUtils.isNotBlank(endName)) {
                regex += "(" + endName + ")?";
            }
            
            AddressBean ab = new AddressBean();
            ab.setRegex(regex.toLowerCase());
            ab.setCode(split[0]);
            String key = null;
            if (split.length >= 4) {
                key = split[3];
            } else {
                key = split[0]; 
            }
            
            if (key != null) {
                List<AddressBean> list = valAddress.get(key); 
                if (list == null) {
                    list = new ArrayList<>(8);
                    valAddress.put(key, list);
                }
                
                list.add(ab);
            }
            
        }
       
        return valAddress;
    }
    
    public static void main(String[] args) {
        
        List<String> provinceList = FileIOUtils.readFile("E:/WorkingSpace/Lenovo/NLU-NER-Extract_PLUS/src/main/resources/dictionary/address/county.txt");
        
        AddressInitLoad ail = new AddressInitLoad();
        
        ail.dataHandle(provinceList);
    }
    

}



