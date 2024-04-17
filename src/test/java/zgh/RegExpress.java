package zgh;

import java.util.regex.Pattern;

import org.junit.Test;

public class RegExpress {

    // 提取部分类型的字符
    @Test
    public void test01() {
        String plates = "湘AH945Z,湘A67785"; // -> 945,67785
        String res = plates.replaceAll("[^\\d,]", "");
        System.out.println(res);
    }

    // IP地址限制
    @Test
    public void test2() {
        String reg = "((127.0)|(192.168)).\\S*";
        System.out.println(Pattern.matches(reg, "127.0.0.1"));
        System.out.println(Pattern.matches(reg, "192.168.0.1"));
        System.out.println(Pattern.matches(reg, "68.161..63.9"));
    }

    @Test
    public void test3() {
        String reg = "\\S+[室部(店))(口))]";
        System.out.println(Pattern.matches(reg, "湖南涉外经济学院综合教学楼招标采购中心主任办公室"));
        System.out.println(Pattern.matches(reg, "湖南涉外经济学院思想政治教育理论教学部"));
        System.out.println(Pattern.matches(reg, "涉外经济学院内街体育场"));
        System.out.println(Pattern.matches(reg, "xxx(涉外经济学院店)"));
        System.out.println(Pattern.matches(reg, "xxx(停车场出口)"));
    }

    @Test
    public void test4() {
        System.out.println(Pattern.matches(".*(路|道|街|巷)\\d*号.*", "玉兰路148号(涧塘地铁站1号口步行270米)"));
        System.out.println(Pattern.matches(".*(路|道|街|巷)\\d*号.*", "玉兰路148号"));
    }

    @Test
    public void test5() {
        System.out.println(Pattern.matches(".*\\d$", "广东省人民医院门诊住院楼眼科579"));
        System.out.println(Pattern.matches(".*\\d$", "广东省人民医院门诊住院楼眼科"));
    }

    public static void main(String[] args) {
        System.out.println(Pattern.matches(".*\\d*", "广东省人民医院门诊住院楼眼科579"));
    }

}