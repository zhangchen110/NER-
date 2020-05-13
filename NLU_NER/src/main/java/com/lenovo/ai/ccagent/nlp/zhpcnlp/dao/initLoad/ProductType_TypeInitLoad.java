package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.ArrayList;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月3日 下午4:58:09
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class ProductType_TypeInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> convertRegex = convertRegex(FileIOUtils.readFile(path + "product-type.txt"));
        NERDataCache.PRODUCT_TYPE_TYPE_REGEX.addAll(convertRegex);
    }
    
    private List<String> convertRegex(List<String> types){
        List<String> regexList = new ArrayList<>(8);
        types.forEach(t -> {
            String regex = NERPublicUtils.convertIntervalBlankRegex(t);
            regexList.add(regex);
        });
        return regexList;
    }

    
    public static void main(String[] args) {
        ProductType_TypeInitLoad p = new ProductType_TypeInitLoad();
        p.init("E:\\WorkingSpace\\Lenovo\\NLU-NER-Extract\\src\\main\\resources\\dictionary\\");
        System.out.println(NERDataCache.PRODUCT_TYPE_TYPE_REGEX.get(0));
    }
}



