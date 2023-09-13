package zgh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import zgh.lucene.LuceneUtil;
import zgh.lucene.bean.Page;

public class TestLucene {
    private static final String dir = "C:/zgh/test/lucene";
    // private static final String dir = "C:/zgh/test/addr_05";
    
    private static final String DATA = "["
    + "{district: '望城区', name:'长沙市公安局望城分局交通警察大队', addr:'长沙市望城区雷锋大道'},"
    + "{district: '望城区', name:'大泽湖派出所', addr:'长沙市望城区雷锋大道旁'},"
    + "]";
    
    @Test
    public void createData() throws Exception {
        IndexWriter writer = null;
        try {
            writer = LuceneUtil.getWriter(dir);
            List<Map> list = JSON.parseArray(DATA, Map.class);
            for (Map<String, String> one : list) {
                Document doc = new Document();
                doc.add(new Field("name", one.get("name"), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("addr", one.get("addr"), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("fulltext", one.get("district") + one.get("name") + one.get("addr"), Field.Store.YES, Field.Index.NOT_ANALYZED));
                boolean exist = LuceneUtil.query(dir, new TermQuery(new Term("name", one.get("name")))).size() > 0;
                if (!exist) {
                    writer.addDocument(doc);
                    System.out.println("added");
                } else {
                    writer.updateDocument(new Term("name", one.get("name")), doc);
                    System.out.println("updated");
                }
            }
        } finally {
            writer.optimize();
            writer.close();
        }
    }
    
    @Test
    public void search01() throws Exception {
        long t1 = System.currentTimeMillis();
        String keyword = "望城区交警大队";
        
        StringBuffer sb = new StringBuffer();
        for (int i=0 ; i<keyword.length(); i++) {
            sb.append("*" + keyword.charAt(i));
        }
        sb.append("*");
        
        Query q = new WildcardQuery(new Term("fulltext", sb.toString()));
        show(LuceneUtil.query(dir, q));
        long t2 = System.currentTimeMillis();
        System.out.println("cost " + (t2 - t1) + " ms");
    }
    
    // 字母的通配符查询
    @Test
    public void search1() throws Exception {
        Query q = new WildcardQuery(new Term("pinyin", "*lsjd*"));
        show(LuceneUtil.query(dir, q));
    }

    // 数字的通配符查询
    @Test
    public void search2() throws Exception {
        Query q = new WildcardQuery(new Term("deptNo", "430101*"));
        show(LuceneUtil.query(dir, q));
    }
    
    //分词查询
    @Test
    public void search3() throws Exception {
        Query q = new QueryParser(Version.LUCENE_30, "addr", new StandardAnalyzer(Version.LUCENE_30)).parse("岳麓区");
        // PhraseQuery q = new PhraseQuery();
        // q.add(new Term("addr", "岳麓区"));

        // Query q = new WildcardQuery(new Term("addr", "*岳麓区*"));
        show(LuceneUtil.query(dir, q));
    }

    @Test
    public void fn2() throws Exception {
        //term查询
        TermQuery q = new TermQuery(new Term("id", "a001"));;
        show(LuceneUtil.query(dir, q));
    }
    
    @Test
    public void fn4() throws Exception {
        //范围查询
        TermRangeQuery q = new TermRangeQuery("age", 10+"", 20+"", true, true);
        show(LuceneUtil.query(dir, q));
    }
    
    @Test
    public void fn4_2() throws Exception {
        //范围查询2
        TermRangeQuery q = new TermRangeQuery("sj", "2017-05-01 00:00:00", "2099-01-01 00:00:00", true, true);
        System.out.println(q);
        show(LuceneUtil.query(dir, q));
    }
    
    @Test
    public void fn5() throws Exception {
        //分页查询
        int ps = 2, pn = 1;
        Query q = LuceneUtil.getParser("addr").parse("湖南");
        Page page = LuceneUtil.queryByPage(dir, q, ps, pn);
        System.out.println(page.getTotal());
        show(page.getList());
    }
    
    @Test
    public void fn6() throws Exception {
        //多条件查询
        BooleanQuery q = new BooleanQuery();
        
        Query q1 = LuceneUtil.getParser("addr").parse("湖南");
        TermRangeQuery q2 = new TermRangeQuery("age", 20+"", 30+"", true, true);

        q.add(q1, Occur.MUST);
        q.add(q2, Occur.MUST);
        
        show(LuceneUtil.query(dir, q));
    }
    
    private static void show(List<Document> docs) {
        //打印显示
        System.out.println("size:"+docs.size());
        for (Document doc : docs) {
            //to map and print
            Map one = new HashMap();
            for (Fieldable f : doc.getFields()) {
                one.put(f.name(), f.stringValue());
            }
            System.out.println(one);
        }
    }
}
