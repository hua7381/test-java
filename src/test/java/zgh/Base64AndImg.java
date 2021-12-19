package zgh;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class Base64AndImg {

    @Test
    public void test1() {
        System.out.println("hello");
        String str = imgToBase64();
        System.out.println("str.length" + str.length());
        base64ToImg(str);
    }

    public static String imgToBase64() {
        File f = new File("c:/t/in.jpg");
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            ImageIO.write(bi, "jpg", stream);
            byte[] bytes = stream.toByteArray();
            return Base64.encodeBase64String(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void base64ToImg(String base64String) {
        try {
            byte[] bytes = Base64.decodeBase64(base64String);
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            ImageIO.write(ImageIO.read(stream), "jpg", new File("c:/t/out.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
