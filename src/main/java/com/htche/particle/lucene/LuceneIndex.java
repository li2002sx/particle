package com.htche.particle.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * Created by jankie on 16/4/11.
 */
public class LuceneIndex {

    public static void indexPost(String id, String name) {
        File indexDir = new File("/Users/xiaoliguo/Desktop/lucene-test/index");
        Analyzer analyzer = new IKAnalyzer();
        TextField postIdField = new TextField("id", id, Field.Store.YES);  // 不要用StringField
        TextField postContentField = new TextField("content", name, Field.Store.YES);

        Document doc = new Document();
        doc.add(postIdField);
        doc.add(postContentField);
        IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        try {
            Directory fsDirectory = FSDirectory.open(indexDir);
            IndexWriter indexWriter = new IndexWriter(fsDirectory, iwConfig);
            indexWriter.addDocument(doc);
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
