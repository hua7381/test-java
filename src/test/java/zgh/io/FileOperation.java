package zgh.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.junit.Test;

import zgh.Tool;

/**
 * 文件操作
 * 
 * @author zhangguihua(hua7381@163.com)
 * @date 2017年12月28日
 */
public class FileOperation {

    private static String root = "c:/zgh/test";

    /**
     * 从classpath读取文本文件
     * 
     * @throws Exception
     */
    @Test
    public void fn1() throws Exception {
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(Tool.getClasspath() + "a.txt"));

            String line = null;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("finished.");

        } finally {
            r.close();
        }
    }

    /**
     * 创建文件夹
     * 
     * @throws Exception
     */
    @Test
    public void fn2() throws Exception {
        File f = new File(root + "/a1/b/d/e/f/g");
        System.out.println(f.mkdirs());
    }

    /**
     * 拷贝文件
     * 
     * @throws Exception
     */
    @Test
    public void fn3() throws Exception {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(root + "/a.txt");
            out = new FileOutputStream(root + "/b.txt");
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) != -1) {
                if (n == 1024) {
                    out.write(buffer);
                } else {
                    byte[] buf2 = new byte[n];
                    for (int i = 0; i < n; i++) {
                        buf2[i] = buffer[i];
                    }
                    out.write(buf2);
                }
            }
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
            try {
                out.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 用文本文件读写java对象
     */
    @Test
    public void fn4() throws Exception {
        ObjectOutputStream out = null;
        try {
            Date d = new Date();
            System.out.println(d);
            out = new ObjectOutputStream(new FileOutputStream(root + "/object.txt"));
            out.writeObject(d);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            }
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(root + "/object.txt"));
            Object o = in.readObject();
            System.out.println(o.getClass());
            System.out.println(o);
        } finally {
            try {
                in.close();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 写字符串到文本文件
     * 
     * @throws Exception
     */
    @Test
    public void fn5() throws Exception {
        String str = "asdf阿斯蒂芬";
        FileWriter writer = new FileWriter(root + "/write.txt");
        try {
            writer.write(str);
            System.out.println("ok");
        } finally {
            writer.close();
        }
    }

}
