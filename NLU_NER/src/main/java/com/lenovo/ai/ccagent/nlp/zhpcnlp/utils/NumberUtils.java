package com.lenovo.ai.ccagent.nlp.zhpcnlp.utils;

import java.math.BigDecimal;

/**
 * @author :zhangchen
 * @createDate :2019年5月27日 下午12:04:36
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class NumberUtils {

    public static double formatDouble(double d, int formatLength){
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(formatLength, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}



