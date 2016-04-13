package com.htche.particle.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jankie on 16/4/10.
 */
public class StringHelper {

    public static boolean isMobile(String input) {

        Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static boolean isMoney(String input) {

        Pattern p = Pattern.compile("^\\d{2,3}(\\.\\d{1,2})?$");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static boolean hasChineseCharacters(String input){
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static String getMobile(String input) {

        String str = "";
        Pattern p = Pattern.compile("(1[0-9])\\d{9}");
        Matcher m = p.matcher(input);
        if(m.find()){
            str = m.group();
        }
        return str;
    }

    public static String getPrice(String input) {

        String str = "";
        Pattern p = Pattern.compile("\\d{2,3}(\\.\\d{1,2})?");
        Matcher m = p.matcher(input);
        if(m.find()){
            str = m.group();
        }
        return str;
    }
}
