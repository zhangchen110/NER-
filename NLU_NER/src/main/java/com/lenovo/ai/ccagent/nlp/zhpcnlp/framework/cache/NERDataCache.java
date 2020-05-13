package com.lenovo.ai.ccagent.nlp.zhpcnlp.framework.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lenovo.ai.ccagent.nlp.zhpcnlp.bean.AddressBean;

import net.sf.json.JSONObject;

/**
 * @author :zhangchen
 * @createDate :2019年5月27日 上午9:54:38
 * @Email :zhangchen17@lenovo.com
 * @Description :
 * 静态缓存
 **/
public class NERDataCache {

    /**
     * ner 软件实体抽取缓存
     */
    public final static Map<String, Map<String, List<String>>> SOFTWARE_NER = new HashMap<>(16);
    
    /**
     * ner 软件实体抽取特殊正则缓存
     */
    public final static List<String> SOFTWARE_NER_REGEX = new ArrayList<>(16);
    
    /**
     * ner 产品型号实体抽取缓存
     */
    public final static Map<String, JSONObject> PRODUCT_TYPE_NER = new HashMap<>(16);
    
    /**
     * ner 蓝屏错误代码实体抽取缓存
     */
    public final static List<String> BSOD_ERROR_CODE = new ArrayList<>(16);
    
    /**
     * ner 操作系统实体抽取缓存
     */
    public final static List<String> OPERATING_SYSTEM = new ArrayList<>(16);
    
    /**
     * ner 时间抽取正则缓存
     */
    public final static List<String> TIME_REGEX = new ArrayList<>(8);
    
    /**
     * ner 产品类别正则抽取实体缓存
     */
    public final static List<String> PRODUCT_TYPE_TYPE_REGEX = new ArrayList<>(8);
    
    /**
     * ner 快捷键正则抽取实体缓存
     */
    public final static List<String> SHORTCUT_KEY_REGEX = new ArrayList<>(8);
    
    /**
     * ner 品牌抽取{联想 , levovo}
     */
    public final static List<String> BRAND_REGEX = new ArrayList<>(8);
    
    /**
     * ner 黑屏错误代码抽取配置缓存
     */
    public final static List<String> BLACK_ERROR_CODE = new ArrayList<>(8);
    
    /**
     * ner 地域（省市区）抽取缓存
     */
    public final static Map<String, Map<String, List<AddressBean>>> ADDRESS_NER = new HashMap<>(8);
    
    /**
     * ner 计算机硬件 抽取缓存
     */
    public final static Map<String, String> COMPUTER_HARDWARE_NER = new LinkedHashMap<String, String>(8);
    
    /**
     * ner 杀毒软件抽取
     */
    public final static List<String> SD_SOFTWARE_NER = new ArrayList<>(8);
    
    /**
     * ner 蓝屏提示抽取
     */
    public final static List<String> BLUE_TIPS_NER = new ArrayList<>(8);
    
    /**
     * ner sn提取
     */
    public final static List<String> SN_NER = new ArrayList<>(8);
}



