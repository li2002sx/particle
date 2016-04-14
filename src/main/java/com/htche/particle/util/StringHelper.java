package com.htche.particle.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    public static boolean hasChineseCharacters(String input) {
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static String getMobile(String input) {

        String str = "";
        Pattern p = Pattern.compile("(1[0-9])\\d{9}");
        Matcher m = p.matcher(input);
        if (m.find()) {
            str = m.group();
        }
        return str;
    }

    public static String getPrice(String input) {

        String str = "";
        Pattern p = Pattern.compile("[1-9]\\d{1,2}(\\.\\d{1})?");
        Matcher m = p.matcher(input);
        List<String> prices = new ArrayList<String>();
        while (m.find()) {
            prices.add(m.group());
        }

        for (int i = prices.size() - 1; i >= 0; i--) {
            BigDecimal price = new BigDecimal(prices.get(i));
            if (price.compareTo(new BigDecimal(30)) == 1 && price.compareTo(new BigDecimal(300)) == -1) {
                str = price.toString();
                break;
            }
        }
        return str;
    }

    public static Integer getCarStatus(String input) {
        int status = 0;
        if (input.contains("现车") || input.contains("现货")) {
            return 1;
        } else if (input.contains("打放税")) {
            return 2;
        } else if (input.contains("期货")) {
            return 3;
        }
        return status;
    }

    public static String getCarFrame(String input) {
        String str = "";
        Pattern p = Pattern.compile("#?\\d{4}");
        Matcher m = p.matcher(input);
        if (m.find()) {
            str = m.group().trim();
        }
        return str;
    }

    public static String html(String content) {
        if (content == null) return "";
        String html = content;

        //    html = html.replace( "'", "&apos;");
        html = html.replaceAll("&", "&amp;");
        html = html.replace("\"", "&quot;");  //"
        html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replace(" ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");
        html = html.replaceAll(">", "&gt;");

        return html;
    }
}
