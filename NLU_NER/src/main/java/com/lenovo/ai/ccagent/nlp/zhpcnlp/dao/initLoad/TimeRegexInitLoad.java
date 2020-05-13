package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.Collections;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2019年6月6日 下午5:44:44
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * ner提取时间正则加载
 **/
public class TimeRegexInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> timeRegexList = FileIOUtils.readFile(path + "time-regex-list.txt");
        // 排序
        Collections.sort(timeRegexList, (o1, o2) -> o2.length() - o1.length());
        NERDataCache.TIME_REGEX.addAll(timeRegexList);
    }

}



