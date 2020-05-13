package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.Collections;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2020年2月13日 下午2:48:57
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 使用正则抽取的ner加载
 **/
public class NERRegexExtractInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        // 杀毒软件
        List<String> sdRegexList = FileIOUtils.readFile(path + "sd-software.txt");
        Collections.sort(sdRegexList, (o1, o2) -> o2.length() - o1.length());
        NERDataCache.SD_SOFTWARE_NER.addAll(sdRegexList);
        // 蓝屏提示
        List<String> blueTipsRegexList = FileIOUtils.readFile(path + "blue_tips.txt");
        Collections.sort(blueTipsRegexList, (o1, o2) -> o2.length() - o1.length());
        NERDataCache.BLUE_TIPS_NER.addAll(blueTipsRegexList);
    }

}



