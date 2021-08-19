package zgh.io;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * java发起上传文件的请求
 */
public class ClientUpload {

    @Test
    public void test1() {
        try {
            String url = "http://127.0.0.1:7173/file/file/one";

            // File file = new File("c:/zgh/test-upload/0a3dcdc.jpg");
            File file = new File("c:/zgh/test-upload/1533393352217.mp4");

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("dir", "test2");
            param.put("divideByDate", "false");

            Map<String, String> httpRes = uploadFile(url, file, param);

            System.out.println("status: " + httpRes.get("status"));
            System.out.println("data: " + httpRes.get("data"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> uploadFile(String url, File file, Map<String, Object> param) {
        Map<String, String> result = new HashMap<String, String>();

        if (param != null) {
            if (url.contains("?")) {
                url += "&";
            } else {
                url += "?";
            }
            url += toQueryStringParams(param);
        }

        try {
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            // 连接成功
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 上传文件
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数, photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            result.put("status", conn.getResponseCode() + "");
            result.put("data", getResData(conn));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static String getResData(HttpURLConnection conn) throws IOException, UnsupportedEncodingException {
        InputStream inout = conn.getInputStream();
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        int bytesout = 0;
        byte[] bufferout = new byte[1024];
        while ((bytesout = inout.read(bufferout)) != -1) {
            infoStream.write(bufferout, 0, bytesout);
        }
        inout.close();
        infoStream.flush();
        infoStream.close();
        // 获取返回数据进行处理
        String resData = infoStream.toString("utf-8");
        return resData;
    }

    private static String toQueryStringParams(Map<String, Object> param) {
        StringBuffer sb = new StringBuffer();
        if (param != null) {
            for (Object key : param.keySet()) {
                Object val = param.get(key);
                if (sb.length() > 0) {
                    sb.append("&");
                }
                try {
                    sb.append(key + "=" + URLEncoder.encode("" + val, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
