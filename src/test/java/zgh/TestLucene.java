package zgh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import zgh.lucene.LuceneUtil;
import zgh.lucene.bean.Page;

public class TestLucene {
    private static final String dir = "C:/zgh/test/lucene";
    
    @Test
    public void fn1() throws Exception {
        IndexWriter writer = null;
        try {
            writer = LuceneUtil.getWriter(dir);
            
            String json = "["
                + "{id:'a001', sj:'2017-05-01 08:27:29', age:'21', name:'张三', addr:'湖南省长沙市望城区银星路555号'},"
                + "{id:'a002', sj:'2017-05-02 08:18:29', age:'18', name:'李四', addr:'北京市长安街天安门'},"
                + "{id:'a003', sj:'2017-05-02 10:24:50', age:'31', name:'王五', addr:'湖南省长沙市岳麓区尖山路中电软件园6栋'},"
                + "{id:'a004', sj:'2017-05-03 17:07:26', age:'16', name:'赵六', addr:'湖南省长沙市岳麓区尖山路中电软件园7栋'},"
                + "{id:'a005', sj:'2017-05-04 11:29:53', age:'27', name:'张工', addr:'湖南省长沙市岳麓区尖山路中电软件园8栋'},"
                + "]";
            List<Map> list = JSON.parseArray(json, Map.class);
            
            for (Map<String, String> one : list) {
                Document doc = new Document();
                doc.add(new Field("id", one.get("id"), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("sj", one.get("sj"), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("name", one.get("name"), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("age", one.get("age"), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("addr", one.get("addr"), Field.Store.YES, Field.Index.ANALYZED));
                
                boolean exist = LuceneUtil.query(dir, new TermQuery(new Term("id", one.get("id")))).size() > 0;
                
                if (!exist) {
                    writer.addDocument(doc);
                    System.out.println("added");
                } else {
                    writer.updateDocument(new Term("id", one.get("id")), doc);
                    System.out.println("updated");
                }
            }
            
        } finally {
            writer.optimize();
            writer.close();
        }
    }
    
    @Test
    //term查询
    public void fn2() throws Exception {
        TermQuery q = new TermQuery(new Term("id", "a001"));;
        show(LuceneUtil.query(dir, q));
    }
    
    @Test
    //分词查询
    public void fn3() throws Exception {
        Query q = LuceneUtil.getParser("addr").parse("岳麓区");
        show(LuceneUtil.query(dir, q));
    }
    
    @Test
    //范围查询
    public void fn4() throws Exception {
        TermRangeQuery q = new TermRangeQuery("age", 10+"", 20+"", true, true);
        show(LuceneUtil.query(dir, q));
    }
    
    @Test
    //范围查询2
    public void fn4_2() throws Exception {
        TermRangeQuery q = new TermRangeQuery("sj", "2017-05-01 00:00:00", "2099-01-01 00:00:00", true, true);
        System.out.println(q);
        show(LuceneUtil.query(dir, q));
    }
    
    @Test
    //分页查询
    public void fn5() throws Exception {
        int ps = 2, pn = 1;
        Query q = LuceneUtil.getParser("addr").parse("湖南");
        Page page = LuceneUtil.queryByPage(dir, q, ps, pn);
        System.out.println(page.getTotal());
        show(page.getList());
    }
    
    @Test
    //多条件查询
    public void fn6() throws Exception {
        BooleanQuery q = new BooleanQuery();
        
        Query q1 = LuceneUtil.getParser("addr").parse("湖南");
        TermRangeQuery q2 = new TermRangeQuery("age", 20+"", 30+"", true, true);

        q.add(q1, Occur.MUST);
        q.add(q2, Occur.MUST);
        
        show(LuceneUtil.query(dir, q));
    }
    
    //打印显示
    private void show(List<Document> docs) {
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
