package com.htche.particle.controller;

import com.google.common.base.Strings;
import com.htche.particle.facade.CityFacade;
import com.htche.particle.facade.InColorFacade;
import com.htche.particle.facade.OutColorFacade;
import com.htche.particle.facade.SpecFacade;
import com.htche.particle.model.AnalyzerInfo;
import com.htche.particle.model.CarTypeInfo;
import com.htche.particle.model.CityInfo;
import com.htche.particle.util.AnalyzerHelper;
import com.htche.particle.util.AppConfigHelper;
import com.htche.particle.util.StringHelper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.*;

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

    private final String _INDEXPATH = AppConfigHelper.nodeMap.get("index_path");

    private final Integer _SUBCOUNT = Integer.parseInt(AppConfigHelper.nodeMap.get("analyzer_length"));

    private final Integer _QUERYCOUNT = Integer.parseInt(AppConfigHelper.nodeMap.get("analyzer_count"));
    ;

    @Autowired
    CityFacade cityFacade;
    @Autowired
    SpecFacade specFacade;
    @Autowired
    OutColorFacade outColorFacade;
    @Autowired
    InColorFacade inColorFacade;

    @RequestMapping("analyzer")
    public ModelAndView analyzer(String input) {

        ModelAndView model = new ModelAndView("custom/analyzer");
        model.addObject("input", input);

        List<AnalyzerInfo> analyzerInfos = new ArrayList<AnalyzerInfo>();

        if (input != null && !input.isEmpty()) {
            List<CityInfo> cityInfos = cityFacade.getCities();

            Map<Integer, String> speces = specFacade.getSpeces();

            List<String> outColors = outColorFacade.getOutColors();

            List<String> inColors = inColorFacade.getInColors();

//            input = input.replaceAll(" +","");
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
                    if (item != null && !item.isEmpty() && StringHelper.regPass(item, "1(4|5|6|7)款?[^\\d]")) {
                        item = item.trim();
                        if (StringHelper.hasChineseCharacters(item)) {

                            //匹配手机
                            String mobile = StringHelper.getMobile(item);
                            if (mobile.isEmpty()) mobile = globalMobile;

                            //匹配价格
                            String price = StringHelper.getPrice(item);

                            //匹配状态
                            Integer status = StringHelper.getCarStatus(item);

                            //匹配车架号
                            List<String> carFrames = StringHelper.getCarFrame(item);
                            if (carFrames.size() == 0) carFrames.add("");

                            //匹配城市
                            CityInfo cityInfo = new CityInfo();
                            for (CityInfo city : cityInfos) {
                                String cityName = city.getCityName();
                                if (!Strings.isNullOrEmpty(cityName)) {
                                    if (item.indexOf(cityName) > -1) {
                                        cityInfo = city;
                                        break;
                                    }
                                }
                            }
                            String outColor = "", inColor = "";

                            int outIndex = -1, index = -1;
                            Map<Integer, String> outMap = new HashMap<Integer, String>();
                            //匹配外/内饰颜色
                            int k = 0;
                            for (String out : outColors) {
                                index = item.indexOf(out);
                                if (index > -1) {
                                    outMap.put(index, out);
                                    if (k == 0 || index < outIndex) {
                                        outIndex = index;
                                    }
                                    k++;
                                }
                            }

                            Map<Integer, String> inMap = new HashMap<Integer, String>();
                            if (outMap.size() > 0 && outIndex > -1) {
                                outColor = outMap.get(outIndex);
                                String filterOutItem = item.substring(outIndex + 1, item.length() - outIndex - 1);
                                outIndex = -1;
                                index = -1;
                                k = 0;
                                for (String in : inColors) {
                                    index = filterOutItem.indexOf(in);
                                    if (index > -1) {
                                        inMap.put(index, in);
                                        if (k == 0 || index < outIndex) {
                                            outIndex = index;
                                        }
                                        k++;
                                    }
                                }
                            }

                            if (inMap.size() > 0 && outIndex > -1) {
                                inColor = inMap.get(outIndex);
                            }

                            //匹配规格
                            Integer spec = StringHelper.getSpec(item);

                            String keywords = item.substring(0, item.length() > _SUBCOUNT ? _SUBCOUNT : item.length());
                            keywords = keywords.replace(outColor,"");
                            keywords = keywords.replace(inColor,"");
                            keywords = keywords.replace("标配","");
                            String analyzeChinese = AnalyzerHelper.analyzeChinese(keywords, true);
                            TokenStream tokenStream = AnalyzerHelper.convertSynonym(analyzeChinese);
                            String result = AnalyzerHelper.displayTokens(tokenStream);

                            if (item.toLowerCase().contains("hse")) {
                                result = String.format("%s HSE", result);
                            }

                            QueryParser queryParser = new QueryParser("content", analyzer);         //使用QueryParser查询分析器构造Query对象
//                            queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
                            Query query = queryParser.parse(result);     // 搜索Lucene
                            TopDocs topDocs = indexSearcher.search(query, _QUERYCOUNT);      //搜索相似度最高的5条记录
                            System.out.println("命中:" + topDocs.totalHits);
                            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                            int hits = topDocs.totalHits;
                            if (hits > _QUERYCOUNT) hits = _QUERYCOUNT;


                            Map<String, String> carTypeMap = new LinkedHashMap<>();
                            for (int i = 0; i < hits; i++) {
                                Document targetDoc = indexSearcher.doc(scoreDocs[i].doc);
                                carTypeMap.put(targetDoc.get("id"), targetDoc.get("content"));
                                System.out.println("内容:" + targetDoc.toString());
                            }

                            for (String carFrame : carFrames) {
                                AnalyzerInfo analyzerInfo = new AnalyzerInfo();
                                analyzerInfo.setKeywords(keywords);
                                analyzerInfo.setIkwords(result);
                                analyzerInfo.setCarTypeMap(carTypeMap);
                                analyzerInfo.setMobile(mobile);
                                analyzerInfo.setPrice(price);
                                analyzerInfo.setStatus(status);
                                analyzerInfo.setCity(cityInfo);
                                analyzerInfo.setSpec(spec);
                                analyzerInfo.setSpecName(speces.get(spec));
                                analyzerInfo.setCarFrame(carFrame);

                                if (!Strings.isNullOrEmpty(outColor) && !Strings.isNullOrEmpty(inColor)) {
                                    analyzerInfo.setOutColor(outColor);
                                    analyzerInfo.setInColor(inColor);
                                }

                                analyzerInfos.add(analyzerInfo);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            model.addObject("speces", speces);
        }
        model.addObject("analyzers", analyzerInfos);
        return model;
    }
}
