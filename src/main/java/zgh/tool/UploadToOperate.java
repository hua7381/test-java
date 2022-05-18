package zgh.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import zgh.util.FileUtil;
import zgh.util.HttpUtil;

public class UploadToOperate {

    public static void main(String[] args) {
        String dir = "c:/t";
        String name = "z_0518a1.rar";

        String base64 = FileUtil.fileToBase64(dir + "/" + name);

        try {
            name = URLEncoder.encode(name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // String url = "http://127.0.0.1:17001/operate/file/uploadByBase64/" + name;
        String url = "http://120.238.112.56:12880/operate/file/uploadByBase64/" + name;
        Map<String, String> hRes = HttpUtil.sendPost(url, base64);
        System.out.println(JSON.toJSONString(hRes));
    }
    
}