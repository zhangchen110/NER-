package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework;

import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.shortcut_key.ShortcutKeyMatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad.config.ConfigNERNameKeyLoad;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.NERPublicUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月3日 下午5:36:48
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 快捷键抽取
 **/
public class NERShortcutKeyExtract extends NERProcessing<String>{

    @Override
    public void initMatchingDataHandle() {
        // TODO Auto-generated method stub
    }

    @Override
    public String extract(String query, Map<String, List<NERResultEntity>> nerExtractResult) {
        MatchingDataHandle mdh = new ShortcutKeyMatchingDataHandle();
        List<NERResultEntity> matchingResult = mdh.matchingResult(query);
        Object property = ConfigNERNameKeyLoad.NER_NAME_KEYS.get("shortcut_key");
        String key = property !=null ? property.toString() : "shortcut_key";
        nerExtractResult.put(key, matchingResult);
        query = NERPublicUtils.dislodgeMatchingNER(query, matchingResult);
        return query;
    }

    @Override
    public boolean isExecute(String query) {
        return true;
    }
}



