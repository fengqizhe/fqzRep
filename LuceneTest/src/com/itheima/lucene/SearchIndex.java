package com.itheima.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchIndex {
	
private void printResult(Query query) throws Exception{
	Directory directory = FSDirectory.open(new File("D:/logs"));
	IndexReader indexReader = DirectoryReader.open(directory);
	IndexSearcher indexSearcher = new IndexSearcher(indexReader);
	TopDocs topDocs = indexSearcher.search(query, 10);
	System.out.println("总记录数"+topDocs.totalHits);
	ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
		int doc2 = scoreDoc.doc;
		Document doc = indexSearcher.doc(scoreDoc.doc);
		System.out.println(doc.get("name"));
		System.out.println(doc.get("size"));
		System.out.println(doc.get("path"));
		System.out.println(doc.get("content"));
	}
	indexReader.close();
}
@Test
public void testMatchAllDocsQuery() throws Exception{
	MatchAllDocsQuery query = new MatchAllDocsQuery();
	System.out.println(query);
	printResult(query);
}
@Test
public void testNumericRangeQuery() throws Exception{
Query query=NumericRangeQuery.newLongRange("size", 0l, 111024l, false, true);
System.out.println(query);
printResult(query);
}
@Test
public void testBooleanQuery() throws Exception{
	BooleanQuery query = new BooleanQuery();
	TermQuery termQuery = new TermQuery(new Term("name","spring"));
	TermQuery termQuery2 = new TermQuery(new Term("content","apache"));
		query.add(termQuery,Occur.MUST);
		query.add(termQuery,Occur.MUST);
		System.out.println(query);
		printResult(query);
}
@Test
public void testQueryParser() throws Exception{
	QueryParser queryParser = new QueryParser("name", new IKAnalyzer());
	Query query = queryParser.parse("spring is a framework mybatis");
	System.out.println(query);
	printResult(query);
}
@Test
public void testMultiFileQueryParser() throws Exception{
	String[] fields ={"name","content"};
	QueryParser queryParser = new MultiFieldQueryParser(fields, new IKAnalyzer());
	Query query = queryParser.parse("name:apache content:apache");
	System.out.println(query);
	printResult(query);
}

}
