package com.htche.particle.facade;

import com.htche.particle.util.FileHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: CityFacade
 * @Package: com.htche.particle.facade
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/15 14:56
 * @version: V1.0
 */
@Component
public class InColorFacade {

    private final String _INCOLORPATH = "config/incolor.txt";

    public List<String> getInColors() {
        List<String> colors = new ArrayList<String>();
        //读取文件内容
        String input = FileHelper.readFile(this.getClass().getClassLoader().getResource(_INCOLORPATH).getPath());
        String[] colorArr = input.split("\\r\\n");
        colors = Arrays.asList(colorArr);
        return colors;
    }
}