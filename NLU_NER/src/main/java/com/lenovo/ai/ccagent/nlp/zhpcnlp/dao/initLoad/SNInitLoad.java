package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2020年3月6日 上午11:35:45
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * sn正则抽取加载
 **/
public class SNInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> readFile = FileIOUtils.readFile(path + "sn-regex.txt");
        NERDataCache.SN_NER.addAll(readFile);
    }

}



