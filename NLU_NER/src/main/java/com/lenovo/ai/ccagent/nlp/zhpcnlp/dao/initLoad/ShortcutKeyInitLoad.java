package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.initLoad;


import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

/**
 * @author :zhangchen
 * @createDate :2019年7月3日 下午5:30:51
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 快捷键加载
 **/
public class ShortcutKeyInitLoad implements NERInitLoad{
    
    @Override
    public void init(String path) {
        NERDataCache.SHORTCUT_KEY_REGEX.addAll(FileIOUtils.readFile(path + "shortcut-key.txt"));
    }
    
}



