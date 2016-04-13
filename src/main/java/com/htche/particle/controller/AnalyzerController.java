package com.htche.particle.controller;

import com.htche.particle.model.AnalyzerInfo;
import com.htche.particle.util.AnalyzerHelper;
import com.htche.particle.util.StringHelper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private final String _INDEXPATH = "/Users/xiaoliguo/Desktop/lucene-test/index";

    private final Integer _SUBCOUNT = 15;

    private final Integer _QUERYCOUNT = 1;

    @RequestMapping("analyzer")
    public ModelAndView analyzer(String input) {

        ModelAndView model = new ModelAndView("custom/analyzer");
        model.addObject("input", input);

        input = input.replaceAll(" +","");

        List<AnalyzerInfo> analyzerInfos = new ArrayList<AnalyzerInfo>();

        if (input != null && !input.isEmpty()) {
            Analyzer analyzer = new IKAnalyzer();
            File indexDir = new File(_INDEXPATH);
            try {
                Directory fsDirectory = FSDirectory.open(indexDir);
                DirectoryReader indexReader = DirectoryReader.open(fsDirectory);
                IndexSearcher indexSearcher = new IndexSearcher(indexReader);

                String globalMobile = StringHelper.getMobile(input);
                //分析输入一共有多少行
                String[] inputArr = input.split("\\r\\n");
                for (String item : inputArr) {
                    if (item != null && !item.isEmpty()) {
                        item = item.trim();
                        if (StringHelper.hasChineseCharacters(item)) {
                            String keywords = item.substring(0, item.length() > _SUBCOUNT ? _SUBCOUNT : item.length());
                            String analyzeChinese = AnalyzerHelper.analyzeChinese(keywords, true);
                            TokenStream tokenStream = AnalyzerHelper.convertSynonym(analyzeChinese);
                            String result = AnalyzerHelper.displayTokens(tokenStream);

                            QueryParser queryParser = new QueryParser("content", analyzer);         //使用QueryParser查询分析器构造Query对象
//            queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
                            Query query = queryParser.parse(result);     // 搜索Lucene
                            TopDocs topDocs = indexSearcher.search(query, _QUERYCOUNT);      //搜索相似度最高的5条记录
                            System.out.println("命中:" + topDocs.totalHits);
                            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                            int hits = topDocs.totalHits;
                            if (hits > _QUERYCOUNT) hits = _QUERYCOUNT;

                            //匹配手机
                            String mobile = StringHelper.getMobile(item);
                            if (mobile.isEmpty()) mobile = globalMobile;

                            //匹配价格
                            String price = StringHelper.getPrice(item);

                            for (int i = 0; i < hits; i++) {
                                AnalyzerInfo analyzerInfo = new AnalyzerInfo();
                                Document targetDoc = indexSearcher.doc(scoreDocs[i].doc);
                                analyzerInfo.setCarTypeId(targetDoc.get("id"));
                                analyzerInfo.setCarType(targetDoc.get("content"));
                                analyzerInfo.setMobile(mobile);
                                analyzerInfo.setPrice(price);
                                analyzerInfos.add(analyzerInfo);
                                System.out.println("内容:" + targetDoc.toString());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        model.addObject("analyzers", analyzerInfos);

        return model;
    }
}
