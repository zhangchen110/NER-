package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;

import net.sf.json.JSONObject;

/**
 * @author :zhangchen
 * @createDate :2019年5月28日 下午4:30:52
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class ProductTypeInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        String zhPT = readProductTypeConfigFile(path + "product-type-zh-list.txt");
        NERDataCache.PRODUCT_TYPE_NER.put("zhProductType", JSONObject.fromObject(zhPT));
        
        String enPT = readProductTypeConfigFile(path + "product-type-en-list.txt");
        NERDataCache.PRODUCT_TYPE_NER.put("enProductType", JSONObject.fromObject(enPT));
    }
    
    private String readProductTypeConfigFile(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}



