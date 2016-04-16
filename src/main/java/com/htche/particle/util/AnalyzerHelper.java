package com.htche.particle.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: AnalyzerHelper
 * @Package: com.htche.particle.util
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/12 11:14
 * @version: V1.0
 */
public class AnalyzerHelper {

    /**
     *  进行中文拆分
     */
    public static String analyzeChinese(String input,boolean userSmart) throws IOException {
        StringBuffer sb = new StringBuffer();
        StringReader reader = new StringReader(input.trim());
        IKSegmenter ikSeg = new IKSegmenter(reader, userSmart);// true　用智能分词　，false细粒度
        for (Lexeme lexeme = ikSeg.next(); lexeme != null; lexeme = ikSeg.next()) {
            sb.append(lexeme.getLexemeText()).append(" ");
        }
        return sb.toString();
    }

    /**
     *
     * 针对上面方法拆分后的词组进行同义词匹配，返回TokenStream
     */
    public static TokenStream convertSynonym(String input) throws IOException {
        Version ver = Version.LUCENE_4_10_4;
        Map<String, String> filterArgs = new HashMap<String, String>();
        filterArgs.put("luceneMatchVersion", ver.toString());
        filterArgs.put("synonyms", "config/synonyms.txt");
        filterArgs.put("expand", "true");
        SynonymFilterFactory factory = new SynonymFilterFactory(filterArgs);
        factory.inform(new FilesystemResourceLoader());
        Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer();
        TokenStream ts = factory.create(whitespaceAnalyzer.tokenStream("someField", input));
        return ts;
    }

    /**
     *
     * 将tokenstream拼成一个特地格式的字符串，交给IndexSearcher来处理
     */
    public static String displayTokens(TokenStream ts) throws IOException {
        StringBuffer sb = new StringBuffer();
        CharTermAttribute termAttr = ts.addAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            String token = termAttr.toString();
            sb.append(token).append(" ");
            System.out.print(token+"|");
//            System.out.print(offsetAttribute.startOffset() + "-" + offsetAttribute.endOffset() + "[" + token + "] ");
        }
        System.out.println();
        ts.end();
        ts.close();
        return sb.toString();
    }
}
