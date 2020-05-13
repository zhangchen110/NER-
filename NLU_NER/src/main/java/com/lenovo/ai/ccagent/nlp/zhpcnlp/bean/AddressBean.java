package com.lenovo.ai.ccagent.nlp.zhpcnlp.bean;
/**
 * @author :zhangchen
 * @createDate :2019年8月6日 上午10:18:21
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 省市区实体类
 **/
public class AddressBean {

    /**
     * 地域的匹配正则
     */
    private String regex;
    /**
     * 地域的code值
     */
    private String code;
    public String getRegex() {
        return regex;
    }
    public void setRegex(String regex) {
        this.regex = regex;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
}



