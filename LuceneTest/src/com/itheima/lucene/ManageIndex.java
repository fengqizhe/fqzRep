package com.itheima.lucene;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ManageIndex {
	//获取索引对象
	private IndexWriter getIndexWriter() throws Exception{
		Directory directory = FSDirectory.open(new File("D:/logs"));
		Analyzer analyzer = new IKAnalyzer();
		IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(Version.LATEST, analyzer));
		return indexWriter;
	}
	@Test
	public void addDocument() throws Exception{
		IndexWriter indexWriter = getIndexWriter();
		Document document = new Document();
		Field fName = new TextField("name", "apache SEO是由英文Search Engine Optimization缩写而来， 中文意译为“搜", Store.YES);
		fName.setBoost(100);
		document.add(fName);
		document.add(new TextField("name", "Spring", Store.YES));
		document.add(new TextField("name", "测试文件名2", Store.YES));
		indexWriter.addDocument(document);
		
		indexWriter.commit();
		indexWriter.close();
	}
	@Test
	public void deleteAllDocument() throws Exception{
		IndexWriter indexWriter = getIndexWriter();
		indexWriter.deleteAll();
		indexWriter.close();
	}
	@Test
	public void deleteDoucmentByQuery() throws Exception{
		IndexWriter indexWriter = getIndexWriter();
		TermQuery query = new TermQuery(new Term("name","spring"));
		indexWriter.deleteDocuments(query);
		indexWriter.close();
	}
	@Test
	public void updateDocument() throws Exception{
		IndexWriter indexWriter = getIndexWriter();
		Document document = new Document();
		document.add(new TextField("name", "跟新后文档", Store.YES));
		document.add(new TextField("content", "跟新后文档内容", Store.YES));
		
		indexWriter.updateDocument(new Term("name","spring"), document);
		indexWriter.close();
	}
}
