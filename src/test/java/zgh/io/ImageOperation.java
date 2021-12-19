package zgh.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * 从url读图片写到本地
 */
public class ImageOperation {

    String url = "http://store.is.autonavi.com/showpic/ab3ab73653b1a16602717fa4101011be";

    @Test
    public void urlToFile() {
        try {
            BufferedImage bi = ImageIO.read(new URL(url));
            boolean ok = ImageIO.write(bi, "jpg", new File("c:/t/a.jpg"));
            System.out.println(ok);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void urlToBase64() {
        try {
            BufferedImage bi = ImageIO.read(new URL(url));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(bytes);
            System.out.println("get base64String, length: " + base64String.length());
            // 验证
            base64ToImgFile(base64String, "jpg", "c:/t/b.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void base64ToImgFile(String base64String, String ext, String path) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            ImageIO.write(ImageIO.read(stream), ext, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
