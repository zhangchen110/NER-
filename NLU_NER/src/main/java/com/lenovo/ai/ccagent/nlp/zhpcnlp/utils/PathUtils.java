package com.lenovo.ai.ccagent.nlp.zhpcnlp.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ResourceUtils;
/**
 * @author :zhangchen
 * @createDate :2019年11月19日 下午1:38:17
 * @Email :zhangchen17@lenovo.com
 * @Description :
 **/
public class PathUtils {

    private static String encoding = "utf-8";
    private static String rootPath = null;

    public static String getEncoding() {
        return encoding;
    }

    public static void setEncoding(String encoding) {
        PathUtils.encoding = encoding;
    }

    public static String transformFilePath(String path) {
        if (StringUtils.isEmpty(path)) {
            return path;
        } else {
            boolean transform = true;
            if (transform) {
                try {
                    URL e = ResourceUtils.getURL("classpath:" + path);
                    path = e.getFile();
                    if (!StringUtils.isEmpty(path)) {
                        File file = new File(path);
                        return file.getPath();
                    }
                } catch (Exception arg3) {
                    arg3.printStackTrace();
                }

                return path;
            } else {
                return path;
            }
        }
    }

    public static String getRoot() {
        return rootPath;
    }

    private static String getRoot1() {
        try {
            URL e = ResourceUtils.getURL("classpath:");
            String path;
            if (e == null) {
                path = getRootNoStart();
                System.out.println("getRoot() path2:" + path);
                return path;
            } else {
                path = e.getFile();
                System.out.println("getRoot() path1:" + path);
                if (StringUtils.isEmpty(path)) {
                    path = getRootNoStart();
                    System.out.println("getRoot() path2:" + path);
                    return path;
                } else {
                    return path;
                }
            }
        } catch (Exception arg1) {
            arg1.printStackTrace();
            return getRootNoStart();
        }
    }

    private static String getRootNoStart() {
        try {
            String path = getClassPathFileNOStart(PathUtils.class);
            return path;
        } catch (Exception arg0) {
            return null;
        }
    }

    private static File getClassFile(Class<?> clazz) {
        URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".class");
        if (path == null) {
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/" + name + ".class");
        }

        return new File(path.getFile());
    }

    private static String getClassPathFileNOStart(Class<?> clazz) {
        File file = getClassFile(clazz);
        int i = 0;

        for (int count = clazz.getName().split("[.]").length; i < count; ++i) {
            file = file.getParentFile();
        }

        if (file.getName().toUpperCase().endsWith(".JAR!")) {
            file = file.getParentFile();
        }

        return file != null && file.getPath().startsWith("file:")
                ? file.getPath().replaceFirst("file:", "") + File.separator
                : "";
    }

    static {
        rootPath = getRoot1();
    }
    
    
    public static void main(String[] args) {
        
        System.out.println(rootPath);
        InputStream in = PathUtils.class.getResourceAsStream("/dictionary/brand.txt");
        byte[] by = new byte[1024];
        int c;
        String result = "";
        try {
            while ((c = in.read(by)) != -1) {
                result += new String(by, 0, c);
            }
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



