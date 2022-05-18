package zgh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class HttpUtil {
    private static final Integer CONNECTTIMEOUT = 10000;
    private static final Integer READTIMEOUT = 10000;
    private static final String STATUS = "status";
    private static final Integer MIN_ERR_CODE = 300;

    public static Map<String, String> sendPost(String url, String jsonParam, int connectTimeout, int readTimeout) {
        return doPost(url, "POST", jsonParam, connectTimeout, readTimeout);
    }

    public static Map<String, String> sendPost(String url, Map<String, Object> param, int connectTimeout,
            int readTimeout) {
        return doPost(url, "POST", JSON.toJSONString(param), connectTimeout, readTimeout);
    }

    public static Map<String, String> sendPost(String url, String jsonParam) {
        return doPost(url, "POST", jsonParam, CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendPost(String url, Map<String, Object> param) {
        return doPost(url, "POST", JSON.toJSONString(param), CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendPut(String url, String jsonParam, int connectTimeout, int readTimeout) {
        return doPost(url, "PUT", jsonParam, connectTimeout, readTimeout);
    }

    public static Map<String, String> sendPut(String url, Map<String, Object> param, int connectTimeout,
            int readTimeout) {
        return doPost(url, "PUT", JSON.toJSONString(param), connectTimeout, readTimeout);
    }

    public static Map<String, String> sendPut(String url, String jsonParam) {
        return doPost(url, "PUT", jsonParam, CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendPut(String url, Map<String, Object> param) {
        return doPost(url, "PUT", JSON.toJSONString(param), CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendGet(String url) {
        return doGet(url, "GET", "", CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendGet(String url, String paramStr) {
        return doGet(url, "GET", paramStr, CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendGet(String url, String paramStr, int connectTimeout, int readTimeout) {
        return doGet(url, "GET", paramStr, connectTimeout, readTimeout);
    }

    public static Map<String, String> sendGet(String url, Map<String, Object> param) {
        return doGet(url, "GET", parseParam(param), CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendGet(String url, Map<String, Object> param, int connectTimeout,
            int readTimeout) {
        return doGet(url, "GET", parseParam(param), connectTimeout, readTimeout);
    }

    public static Map<String, String> sendDelete(String url, String paramStr, int connectTimeout, int readTimeout) {
        return doGet(url, "DELETE", paramStr, connectTimeout, readTimeout);
    }

    public static Map<String, String> sendDelete(String url, Map<String, Object> param, int connectTimeout,
            int readTimeout) {
        return doGet(url, "DELETE", parseParam(param), connectTimeout, readTimeout);
    }

    public static Map<String, String> sendDelete(String url, String paramStr) {
        return doGet(url, "DELETE", paramStr, CONNECTTIMEOUT, READTIMEOUT);
    }

    public static Map<String, String> sendDelete(String url, Map<String, Object> param) {
        return doGet(url, "DELETE", parseParam(param), CONNECTTIMEOUT, READTIMEOUT);
    }

    private static Map<String, String> doPost(String url, String method, String jsonParam, int connectTimeout,
            int readTimeout) {
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        Map<String, String> result = new HashMap<String, String>(10);
        String data = "";
        try {
            // 建立连接
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // conn.setRequestProperty("connection", "keep-alive");
            // conn.setRequestProperty("Accept", "application/json");
            // 设置不要缓存
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            conn.connect();

            // write
            writer = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            writer.write(jsonParam);
            writer.flush();

            result.put("status", conn.getResponseCode() + "");

            try {

                if (conn.getResponseCode() >= MIN_ERR_CODE) {
                    // 获取错误原因
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
                } else {
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                }

                String lines;
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    data += lines;
                }
                result.put("data", data);

            } catch (Exception e1) {
                System.out.println(e1.getMessage());
                // ignore
            }

            conn.disconnect();

        } catch (SocketTimeoutException e) {
            result.put("status", "408");
            result.put("data", "连接超时");
            result.put("url", url);
            return result;
        } catch (ConnectException e) {
            result.put("status", "408");
            result.put("data", "连接超时");
            result.put("url", url);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            if (result.get(STATUS) != null) {
                result.put("data", e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                // ignore
            }
        }
        return result;
    }

    private static String parseParam(Map<String, Object> param) {
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

    private static Map<String, String> doGet(String url, String method, String paramStr, int connectTimeout,
            int readTimeout) {
        BufferedReader reader = null;
        Map<String, String> result = new HashMap<String, String>(10);
        String data = "";
        try {
            if (paramStr != null && paramStr.length() > 0) {
                if (url.contains("?")) {
                    url += "&";
                } else {
                    url += "?";
                }
                url += paramStr;
            }
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod(method);
            // conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0;
            // Windows NT 5.1;SV1)");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            conn.connect();

            result.put("status", conn.getResponseCode() + "");

            if (conn.getResponseCode() >= MIN_ERR_CODE) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                data += line;
            }
            result.put("data", data);
        } catch (SocketTimeoutException e) {
            result.put("status", "408");
            result.put("data", "连接超时");
            result.put("url", url);
            return result;
        } catch (ConnectException e) {
            result.put("status", "408");
            result.put("data", "连接超时");
            result.put("url", url);
            return result;
        } catch (Exception e) {
            if (result.get(STATUS) != null) {
                result.put("data", e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e2) {
                // ignore
            }
        }
        return result;
    }
}