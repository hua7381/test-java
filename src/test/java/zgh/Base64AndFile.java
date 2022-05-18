package zgh;

import org.junit.Test;

import zgh.util.FileUtil;

public class Base64AndFile {

    @Test
    public void test1() {
        String base64Str = FileUtil.fileToBase64("c:/t/z_0512c8.rar");
        System.out.println("len: " + base64Str.length());
        FileUtil.base64ToFile(base64Str, "c:/t", "tar.rar");
    }

}