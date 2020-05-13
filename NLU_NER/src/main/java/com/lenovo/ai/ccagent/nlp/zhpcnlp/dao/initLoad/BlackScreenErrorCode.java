package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.Collections;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.black_code.BlackCodeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月18日 上午10:13:23
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 黑屏错误代码  词典文件加载
 **/
public class BlackScreenErrorCode implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> blackCodes = FileIOUtils.readFile(path + "black-screen-error-code.txt");
        BlackCodeMatchingDataHandle handle = new BlackCodeMatchingDataHandle();
        blackCodes.forEach(bc -> NERDataCache.BLACK_ERROR_CODE.add(handle.dataHandle(bc)));
        // 排序
        Collections.sort(NERDataCache.BLACK_ERROR_CODE, (b1, b2) -> b2.length() - b1.length());
    }

}



