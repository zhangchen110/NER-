package com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.sn;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.dao.handle.MatchingDataHandle;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.entity.NERResultEntity;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache.NERDataCache;
import com.lenovo.ai.ccagent.nlp.zhpcnlp.utils.FileIOUtils;

import net.sf.json.JSONArray;

/**
 * @author :zhangchen
 * @createDate :2019年6月4日 下午4:03:27
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * sn抽取处理
 **/
public class SNMatchingDataHandle implements MatchingDataHandle{

    @Override
    public String dataHandle(String str) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NERResultEntity> matchingResult(String query) {
        if (query == null || query.length() == 0) 
            return new ArrayList<>();
        
        List<NERResultEntity> result = new ArrayList<>(8);
        // 将query中的不是规定的sn中可以出现的字符切割开来
        String[] wordsArr = Pattern.compile("[^a-zA-Z0-9 -]").split(query);
        // 匹配sn的正则
        String snRegex = NERDataCache.SN_NER.get(0);
        Pattern pattern = Pattern.compile(snRegex);
        for (String w : wordsArr) {
            if (StringUtils.isBlank(w)) continue;
            // 将所有的空格都去除
            w = w.replace(" ", "");
            Matcher matcher = pattern.matcher(w);
            while (matcher.find()) {
                NERResultEntity nerEntity = new NERResultEntity();
                nerEntity.setName(matcher.group());
                result.add(nerEntity);
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        List<String> readFile = FileIOUtils.readFile("E:/WorkingSpace/Lenovo/NLU-NER-Extract_PLUS/src/main/resources/dictionary/sn-regex.txt");
        NERDataCache.SN_NER.addAll(readFile);
        SNMatchingDataHandle sn = new SNMatchingDataHandle();
        String query = "你好啊，我电脑的序列号是:3M04353B4962021,1S65A9HCT1CTU0512994,"
                + "2572MB1V1AC395,2448MB6V818782   60A6MAR2CNVN489564";
    
       
        System.out.println(JSONArray.fromObject(sn.matchingResult(query.toLowerCase())));
    }

}



