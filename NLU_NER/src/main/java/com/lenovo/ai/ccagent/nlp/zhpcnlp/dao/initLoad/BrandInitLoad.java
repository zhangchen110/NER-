package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.ArrayList;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月3日 下午5:40:26
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class BrandInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> convertRegex = convertRegex(FileIOUtils.readFile(path + "brand.txt"));
        NERDataCache.BRAND_REGEX.addAll(convertRegex);
    }
    
    private List<String> convertRegex(List<String> types){
        List<String> regexList = new ArrayList<>(8);
        types.forEach(t -> {
            String regex = NERPublicUtils.convertIntervalBlankRegex(t);
            regexList.add(regex);
        });
        return regexList;
    }

}



