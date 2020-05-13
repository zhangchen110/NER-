package com.lenovo.ai.ccagent.nlp.zhpcnlp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.service.NERExtractService;

import net.sf.json.JSONObject;

/**
 * @author :zhangchen
 * @createDate :2019年5月29日 上午10:19:53
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
@RestController
public class NERExtractController {
    
    @Autowired
    private NERExtractService nerExtractService;

    /**
     * ner实体命名识别抽取
     * @return
     */
    @PostMapping("/nerExtract")
    public String nerExtract(@RequestBody String jsonRequest){
        String query = "";
        try {
            JSONObject fromObject = JSONObject.fromObject(jsonRequest);
            query = fromObject.getString("query");
        } catch (Exception e) {
            return "{\"state\":\"-1\",\"msg\":\"Parameter error!\"}";
        }
        
        return nerExtractService.nerExtract(query);
    }
}



