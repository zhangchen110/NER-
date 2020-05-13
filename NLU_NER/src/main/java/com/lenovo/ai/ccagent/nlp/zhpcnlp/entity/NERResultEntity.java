package com.lenovo.ai.ccagent.nlp.zhpcnlp.entity;
/**
 * @author :zhangchen
 * @createDate :2019年5月21日 上午10:30:13
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class NERResultEntity {

    private String name;
    
    private double condifence = 1.0;
    
    private String regType = "rule";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCondifence() {
        return condifence;
    }

    public void setCondifence(double condifence) {
        this.condifence = condifence;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }
    
    public String toString(){
        return "name : " + name + "\r\n"
                + "condifence : " + condifence + "\r\n"
                + "regType : " + regType + "\r\n";
                
    }
}



