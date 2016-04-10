package com.htche.particle.controller;

import com.htche.particle.facade.CarFacade;
import com.htche.particle.model.CarInfo;
import com.htche.particle.model.InvokeResult;
import com.htche.particle.model.ParticleResult;
import com.htche.particle.util.FileHelper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: ParticleController
 * @Package: com.htche.particle.controller
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/8 17:42
 * @version: V1.0
 */
@Controller
@RequestMapping("/")
public class ParticleController {

    @Autowired
    CarFacade carFacade;

    @RequestMapping("particle")
    public ModelAndView particle(String ugc) {

        ModelAndView model = new ModelAndView("custom/particle");
        model.addObject("ugc", ugc);

        List<ParticleResult> resultList = new ArrayList<ParticleResult>();
        CarInfo carInfo = new CarInfo();

        if (ugc != null && !ugc.isEmpty()) {

            //构建IK分词器，使用smart分词模式
            Analyzer analyzer = new IKAnalyzer(true);

            //获取Lucene的TokenStream对象
            TokenStream ts = null;
            try {
                ts = analyzer.tokenStream("myfield", new StringReader(ugc));
                //获取词元位置属性
                OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
                //获取词元文本属性
                CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
                //获取词元文本属性
                TypeAttribute type = ts.addAttribute(TypeAttribute.class);


                //重置TokenStream（重置StringReader）
                ts.reset();
                //迭代获取分词结果
                while (ts.incrementToken()) {

                    ParticleResult result = new ParticleResult();
                    result.setRange(offset.startOffset() + " - " + offset.endOffset());
                    result.setTerm(term.toString());
                    result.setType(type.type());

                    resultList.add(result);
                }
                //关闭TokenStream（关闭StringReader）
                ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //释放TokenStream的所有资源
                if (ts != null) {
                    try {
                        ts.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(resultList.size()>0){
                carInfo = carFacade.analyzeUGC(resultList);
            }
        }
        model.addObject("data", resultList);
        model.addObject("car", carInfo);
        return model;
    }

    @RequestMapping("addExtDics")
    public
    @ResponseBody
    InvokeResult addExtDics(String terms) {
        InvokeResult result = new InvokeResult();
        try {
            if (terms != null && !terms.isEmpty()) {
                String[] termArr = terms.split("\\n");
                List<String> termList = Arrays.asList(termArr);
                String path = this.getClass().getClassLoader().getResource("ext.dic").getPath();
                FileHelper.fileOutputStream(path, termList);
                result.setSuccess(true);
            }
        } catch (IOException ex) {
            result.setError(ex.getMessage());
            throw new RuntimeException(ex);
        }

        return result;
    }
}
