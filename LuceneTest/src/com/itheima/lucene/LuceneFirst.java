package com.itheima.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneFirst {
@SuppressWarnings("resource")
@Test
public void createIndex() throws Exception{
	//创建字典 指定索引库保存位置aa
	Directory directory = FSDirectory.open(new File("D:/logs"));
	//创建解析器
	Analyzer analyzer =new IKAnalyzer(); 
	//创建索引配置
	IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
	//创建索引
	IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
	File file = new File("D:/黑马文件/面试宝典！！/01_java基础");

	for (File f:file.listFiles()) {
	String filename = f.getName();
	String path = f.getPath();
	String fileContent = FileUtils.readFileToString(f);
	Long fileSize = FileUtils.sizeOf(f);
	Document document = new Document();
	TextField fName = new TextField("name", filename, Store.YES);
	TextField fPath = new TextField("path", path,Store.YES);
	TextField fContent = new TextField("content", fileContent, Store.NO);
	LongField fSize = new LongField("size",fileSize.longValue(), Store.YES);
	document.add(fName);
	document.add(fPath);
	document.add(fContent);
	document.add(fSize);
	indexWriter.addDocument(document);
}
indexWriter.commit();
indexWriter.close();
}
@Test
public void queryIndex() throws Exception{
	Directory directory = FSDirectory.open(new File("D:/logs"));
	//创建索引阅读器
	IndexReader indexReader = DirectoryReader.open(directory);
	IndexSearcher indexSearcher = new IndexSearcher(indexReader);
	Query query = new TermQuery(new Term("name", "笔试"));
	TopDocs topDocs = indexSearcher.search(query, 10);
	System.out.println(topDocs.totalHits);
	ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	for (ScoreDoc scoreDoc:scoreDocs) {
		int doc = scoreDoc.doc;
		Document document = indexSearcher.doc(doc);
		System.out.println(document.get("name"));
		System.out.println(document.get("content"));
		System.out.println(document.get("path"));
		System.out.println(document.get("size"));
	}
	indexReader.close();
}
@SuppressWarnings("resource")
@Test
public void testAnalyzer() throws Exception{
	Analyzer analyzer = new IKAnalyzer();
	TokenStream tokenStream = analyzer.tokenStream("", "Lucene是一个台独非常优秀的开源的全文搜索"
			+ "引擎传智播客摩拜单车;我们可以在它的上面开发出各种全文搜索的应用来。"
			+ "Lucene在国外有很高的知名度; 现在已经是Apache的顶级项目; 在国内;");
	tokenStream.reset();
	CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);
	while(tokenStream.incrementToken()){
		System.out.println(attribute.toString());
	}
	tokenStream.close();
}

}
