package com.geaosu.huanxin.utils;

import java.util.regex.Pattern;

public class StringUtiles {

    /**
     * 正则表达式 - 判断字符串是不是中文
     */
    public static boolean isChStr(String str) {
        String pattern = "/[\\u4E00-\\u9FA5]/g";
        boolean matches = Pattern.matches(pattern, str);
        return matches;
    }

    /**
     * 正则表达式 - 判断字符串是不是英文字母
     */
    public static boolean isEnStr(String str) {
        String pattern = "^[a-z]+$";
        boolean matches = Pattern.matches(pattern, str);
        return matches;
    }

    /**
     * 正则表达式 - 判断字符串是不是英文字母
     */
    public static void delectStrLastIndexStr(String str) {
        String pattern = "^[a-z]+$";
        boolean matches2 = Pattern.matches(pattern, str);
    }


}
