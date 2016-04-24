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

        input = input.replaceAll("(1[0-9])\\d{9}", "").replaceAll("#?\\d{4}#?", "");
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

    public static boolean regPass(String input, String reg) {

        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static Integer getCarStatus(String input) {
        int status = 0;
        if (regPass(input, "现(车|货)") || regPass(input, "当天(开)?票")
                || regPass(input, "手续齐(全)?") || regPass(input, "拆箱")) {
            return 1;
        } else if (input.contains("打放税") || input.contains("马上齐")
                || input.contains("到港") || regPass(input, "\\d{1,2}个工作日")) {
            return 2;
        } else if (input.contains("期货") || input.contains("在途")
                || input.contains("发船") || regPass(input, "\\d{1,2}月d{1,2}日")
                || input.contains("交车")) {
            return 3;
        }
        return status;
    }

    public static List<String> getCarFrame(String input) {

        List<String> carFrames = new ArrayList<String>();
        input = input.replaceAll("(1[0-9])\\d{9}", "");
        input = input.replaceAll("(27|30|40|45|46|57)00", "");
        String str = "";
        Pattern p = Pattern.compile("#?\\d{4}#?");
        Matcher m = p.matcher(input);
        while (m.find()) {
            str = m.group().trim();
            carFrames.add(str);
        }
        return carFrames;
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

    public static Integer getSpec(String input) {
        int spec = 0;
        if (regPass(input, "美(版|规|国规格)") || regPass(input, "美规(车)")) {
            return 1;
        } else if (regPass(input, "欧(版|规|规车|洲规格)")) {
            return 2;
        } else if (regPass(input, "中东(版)?") || regPass(input, "迪拜(版)?")
                || regPass(input, "迪拜(版)?") || regPass(input, "阿曼(版)?")
                || regPass(input, "科威特(版)?") || regPass(input, "黎巴嫩(版)?")
                || regPass(input, "黎巴嫩(版)?") || regPass(input, "巴林(版)?")
                || regPass(input, "沙特(版)?")) {
            return 3;
        } else if (regPass(input, "墨(版|规|西哥版)")) {
            return 4;
        } else if (regPass(input, "加(版|规|拿大版|拿大规格)")) {
            return 5;
        } else if (regPass(input, "德(版|规|国版|国规格)")) {
            return 8;
        } else if (regPass(input, "中(版|规|国版|国规格)")) {
            return 9;
        }

        return spec;
    }
}
