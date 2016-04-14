package com.htche.particle.controller;

import com.htche.particle.facade.CarFacade;
import com.htche.particle.model.CarInfo;
import com.htche.particle.model.InvokeResult;
import com.htche.particle.model.ParticleResult;
import com.htche.particle.util.AnalyzerHelper;
import com.htche.particle.util.FileHelper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
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
public class ExtDictController {

    private final String _EXTDICTPATH = "ext.dic";

    @RequestMapping("extdict")
    public ModelAndView extdict(String input) {

        ModelAndView model = new ModelAndView("custom/extdict");
        String path = this.getClass().getClassLoader().getResource(_EXTDICTPATH).getPath();
        String extDict = FileHelper.readFile(path);
        model.addObject("data",extDict);
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
