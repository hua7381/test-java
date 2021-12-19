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

}