package com.htche.particle.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jankie on 16/4/10.
 */
public class StringHelper {

    public static boolean isMobile(String mobile) {

        Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean isMoney(String item) {

        Pattern p = Pattern.compile("^\\d{2,3}(\\.\\d{1,2})?$");
        Matcher m = p.matcher(item);
        return m.matches();
    }
}
