package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;

import java.util.ArrayList;
import java.util.List;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.bsod.BsodErrorCodeMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;


/**
 * @author :zhangchen
 * @createDate :2019年5月30日 下午2:08:41
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * ner抽取
 * 蓝屏错误代码列表
 * 加载
 **/
public class BsodErrorCodeInitLoad implements NERInitLoad{

    @Override
    public void init(String path) {
        List<String> errorCodeList = FileIOUtils.readFile(path + "bsod-error-code.txt");
        List<String> convertRegex = convertRegex(errorCodeList);
        NERDataCache.BSOD_ERROR_CODE.addAll(convertRegex);
    }
    
    private List<String> convertRegex(List<String> errorCodeList){
        List<String> regexList = new ArrayList<>(16);
        BsodErrorCodeMatchingDataHandle mdh = new BsodErrorCodeMatchingDataHandle();
        errorCodeList.stream().forEach(e -> {
            regexList.add(mdh.dataHandle(e));
        });
        
        return regexList;
    }
}



