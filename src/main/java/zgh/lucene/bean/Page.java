package zgh.lucene.bean;

import java.util.List;

/**
 * 分页结果
 * @author zgh
 *
 */
public class Page<T> {
    private Integer total;
    private List<T> list;
    public Page() {
    }
    public Page(Integer total, List<T> list) {
        this.total = total;
        this.list = list;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List getList() {
        return list;
    }
    public void setList(List list) {
        this.list = list;
    }
}
