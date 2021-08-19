package zgh.lucene;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import zgh.lucene.bean.Page;

public class LuceneUtil {
    private static int TOP_NUM = 10000;
    
    /**
     * 获取writer
     * @param dir 索引文件的目录
     * @return
     */
    public static IndexWriter getWriter(String dir) throws Exception {
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(
                    FSDirectory.open(new File(dir)), 
                    new StandardAnalyzer(Version.LUCENE_30), false,
                    IndexWriter.MaxFieldLength.LIMITED);
        } catch (java.io.FileNotFoundException e) {//如果不存在则获取“创建式”的writer
            writer = new IndexWriter(
                    FSDirectory.open(new File(dir)), 
                    new StandardAnalyzer(Version.LUCENE_30), true,
                    IndexWriter.MaxFieldLength.LIMITED);
        }
        return writer;
    }
    
    /**
     * 查询
     * @param dir
     * @param query
     * @return
     */
    public static List<Document> query(String dir, Query query) {
        List<Document> result = new ArrayList<Document>();
        
        IndexSearcher searcher = null;
        try {
            searcher = new IndexSearcher(FSDirectory.open(new File(dir)), true);
            TopScoreDocCollector collector = TopScoreDocCollector.create(TOP_NUM, false);
            searcher.search(query, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            for (int i=0; i< hits.length; i++) {
                Document doc = searcher.doc(hits[i].doc);
                result.add(doc);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    /**
     * 分页查询
     * @param dir
     * @param query
     * @param ps pageSize
     * @param pn pageNum
     * @return
     */
    public static Page queryByPage(String dir, Query query, int ps, int pn) {
        List<Document> result = new ArrayList<Document>();
        IndexSearcher searcher = null;
        try {
            searcher = new IndexSearcher(FSDirectory.open(new File(dir)), true);
            TopScoreDocCollector collector = TopScoreDocCollector.create(TOP_NUM, false);
            searcher.search(query, collector);
            
            int total = collector.getTotalHits();
            int maxPn = (total-1)/ps+1;
            if (pn > maxPn) {
                pn = maxPn;
            }
            
            ScoreDoc[] hits = collector.topDocs((ps*(pn-1)), ps).scoreDocs;
            for (int i=0; i< hits.length; i++) {
                Document doc = searcher.doc(hits[i].doc);
                result.add(doc);
            }
            return new Page(total, result);
        } catch(Exception e) {
            throw new RuntimeException("hello");
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 查询单个
     * @param indexDir
     * @param query
     * @return
     */
    public static Document queryOne(String indexDir, Query query) {
        Document result = null;
        List<Document> list = LuceneUtil.query(indexDir, query);
        if (list != null && list.size()>0) {
            result = list.get(0);
        }
        return result;
    }
    
    /**
     * 获取分词器
     * @param field
     * @return
     */
    public static QueryParser getParser(String field) {
        return new QueryParser(Version.LUCENE_30, field, new StandardAnalyzer(Version.LUCENE_30));
    }
    
    /**
     * 处理显示的文本
     * @param content
     * @param keyword
     * @return
     */
    public static String handelContent(String content, String keyword) {
        //省略长文本
        int index = content.indexOf(keyword);//关键词第一次出现位置
        if (index == -1) {
            index = 0;
        }
        String before = content.substring(0, index);//第一个关键词之前的文本
        int index1 = before.lastIndexOf("。")+1;
        int index2 = before.lastIndexOf(":")+1;
        int start = (index1>index2) ? index1 : index2;//关键词之前的文本中最后一个标点的位置
        //关键词之前长于20位的文本用省略符代替
        String dotsBefore = "";
        if (index-start > 20) {
            start = index - 20;
            dotsBefore = "……";
        }
        content = dotsBefore + content.substring(start);
        //整段文本长于150的部分用省略符代替
        if (content.length() > 150) {
            content = content.substring(0, 130) + "……";
        }
        
        //关键字高亮显示
        for (String keywordPart : keyword.split(" ")) {
            content = content.replace(keywordPart, "<span class='highLight'>"+keywordPart+"</span>");//高亮显示关键词
        }
        return content;
    }
    
    /**
     * 转换层级代码，在每一位间插入分隔符，以便分词
     * @param code
     * @return
     */
    public static String parseLevelCode(String code) {
        String newUnit = "START";//起始标志
        if (code!=null && !"".equals(code)) {
            for (int i=0; i<code.length(); i++) {
                newUnit += code.charAt(i)+"#";
            }
        }
        return newUnit;
    }
    
}