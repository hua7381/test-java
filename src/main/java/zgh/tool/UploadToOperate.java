package zgh.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import zgh.util.FileUtil;
import zgh.util.HttpUtil;

public class UploadToOperate {

    public static void main(String[] args) {
        String dir = "c:/t";
        String name = "z_0518a1.rar";
        String url = "http://120.238.112.56:12880/operate/file/uploadByBase64/" + name;

        long t1 = System.currentTimeMillis();
        String base64 = FileUtil.fileToBase64(dir + "/" + name);
        System.out.println("len: " + base64.length());

        try {
            name = URLEncoder.encode(name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> hRes = HttpUtil.sendPost(url, base64);

        System.out.println("hRes: " + JSON.toJSONString(hRes));
        long t2 = System.currentTimeMillis();
        System.out.println("cost " + (t2 - t1) + " ms");
    }
    
}