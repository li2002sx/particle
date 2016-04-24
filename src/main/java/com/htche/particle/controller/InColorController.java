package com.htche.particle.controller;

import com.htche.particle.model.InvokeResult;
import com.htche.particle.util.FileHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: SynonymsController
 * @Package: com.htche.particle.controller
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/14 14:36
 * @version: V1.0
 */
@Controller
@RequestMapping("/")
public class InColorController {

    private final String _INCOLORPATH = "config/incolor.txt";

    @RequestMapping("incolor")
    public ModelAndView incolor() {

        ModelAndView model = new ModelAndView("custom/incolor");
        //读取文件内容
        String input = FileHelper.readFile(this.getClass().getClassLoader().getResource(_INCOLORPATH).getPath());
        model.addObject("input", input);
        return model;
    }

    @RequestMapping("addIncolor")
    public
    @ResponseBody
    InvokeResult addIncolor(String input) {
        InvokeResult result = new InvokeResult();
        if (input != null && !input.isEmpty()) {
            String path = this.getClass().getClassLoader().getResource(_INCOLORPATH).getPath();
            FileHelper.writeFile(path,input);
            result.setSuccess(true);
        }
        return result;
    }
}
