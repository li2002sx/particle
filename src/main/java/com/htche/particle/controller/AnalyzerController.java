package com.htche.particle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: AnalyzerController
 * @Package: com.htche.particle.controller
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/13 18:22
 * @version: V1.0
 */
@Controller
@RequestMapping("/")
public class AnalyzerController {

    @RequestMapping("analyzer")
    public ModelAndView analyzer(String input) {

        ModelAndView model = new ModelAndView("custom/analyzer");
        return model;
    }
}
