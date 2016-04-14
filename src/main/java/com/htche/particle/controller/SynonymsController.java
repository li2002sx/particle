package com.htche.particle.controller;

import com.htche.particle.model.InvokeResult;
import com.htche.particle.util.FileHelper;
import com.htche.particle.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
public class SynonymsController {

    private final String _SYNONYMSPATH = "config/synonyms.txt";

    @RequestMapping("synonyms")
    public ModelAndView synonyms() {

        ModelAndView model = new ModelAndView("custom/synonyms");
        //读取文件内容
        String input = FileHelper.readFile(this.getClass().getClassLoader().getResource(_SYNONYMSPATH).getPath());
        model.addObject("input", input);
        return model;
    }

    @RequestMapping("addSynonyms")
    public
    @ResponseBody
    InvokeResult addSynonyms(String input) {
        InvokeResult result = new InvokeResult();
        if (input != null && !input.isEmpty()) {
            String path = this.getClass().getClassLoader().getResource(_SYNONYMSPATH).getPath();
            FileHelper.writeFile(path,input);
            result.setSuccess(true);
        }
        return result;
    }
}
