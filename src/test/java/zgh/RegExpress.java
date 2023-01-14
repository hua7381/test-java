package zgh;

import java.util.regex.Pattern;

import org.junit.Test;

public class RegExpress {
    
    @Test
    public void test1() {
        String regex = "[京津晋冀蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼渝川贵云藏陕甘青宁新]{0, 1}[\\dABCDEFGHJKLNMxPQRSTUVWXYZ*]{1, 6}[\\dABCDEFGHJKLNMxPQRSTUVWXYZ]{0, 2}";
        System.out.println(Pattern.matches(regex, "湘AH945Z"));
        System.out.println(Pattern.matches(regex, "湘A*45Z"));
        System.out.println(Pattern.matches(regex, "*45Z"));
        System.out.println(Pattern.matches(regex, "湘AH阿斯蒂芬945Z"));
    }

    @Test
    public void test2() {
        String reg = "((127.0)|(172)|(192.168)).\\S*";
        String str = "127.0.0.1";
        Boolean res = Pattern.matches(reg, str);
        System.out.println(res);
    }

}