package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.os.OperatingSystemMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2019年5月31日 下午2:20:16
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class OperatingSystemInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> readData = FileIOUtils.readFile(path + "operating-system-list.txt");
        OperatingSystemMatchingDataHandle osmd = new OperatingSystemMatchingDataHandle();
        List<String> cache = new ArrayList<String>(16);
        readData.forEach(e -> {
            cache.add(osmd.dataHandle(e));
        });
        // 排序
        Collections.sort(cache, (e1, e2) -> e2.length() - e1.length());
        NERDataCache.OPERATING_SYSTEM.addAll(cache);
    }

}



