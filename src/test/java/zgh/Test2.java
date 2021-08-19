package zgh;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Test2
 */
public class Test2 {

  public static void main(String[] args) {
    Map<String, Object> params = new HashMap<String, Object>();
    String url = "http://127.0.0.1/chat/dialog/page?pageNum=1&pageSize=100";
    // String url = "http://127.0.0.1/chat/heartbeat";
    Map<String, Object> result = doGet(url, "GET", params, 999000, 999000);
    System.out.println("");
    System.out.println(result);
  }

  public static Map<String, Object> doGet(String url, String method, Map<String, Object> params, int connectTimeout, int readTimeout) {
        String param = parseParam(params);

        BufferedReader in = null;
        Map<String, Object> result = new HashMap<String, Object>();
        String data = "";
        try {
            if (param != null && param.length() > 0) {
                url += "?" + param;
            }
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod(method);
            // conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0;
            // Windows NT 5.1;SV1)");
            conn.setConnectTimeout(connectTimeout);
      conn.setReadTimeout(readTimeout);

            conn.setRequestProperty("token", "D71825CDA6334E79BC49640770251BEB");

      conn.connect();
      

            result.put("status", conn.getResponseCode());

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                data += line;
            }
            result.put("data", data);
        } catch (SocketTimeoutException e) {
            result.put("status", 408);
            result.put("data", "连接超时");
            result.put("url", url);
            return result;
        } catch (ConnectException e) {
            result.put("status", 408);
            result.put("data", "连接超时");
            result.put("url", url);
            return result;
        } catch (Exception e) {
            if (result.get("status") != null) {
                result.put("data", e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                // ignore
            }
        }
        return result;
  }
  
  private static String parseParam(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Object key : params.keySet()) {
                Object val = params.get(key);
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