package zgh;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Test1 {
    
    public static String encrypt(String password, String userName) {
        try {
            if (null == password || "".equals(password)) {
                return "";
            }
            password = password.replaceAll("[{}|~]", "");
            String openPwd = "";
            String us = userName;
            String ps = password;
            int pwdlen;
            if (us.length() <= 0) {
                us = "ADMIN";
            }
            pwdlen = password.length();
            while (pwdlen > us.length()) {
                us = us + us;
            }

            int op, i;
            for (i = 1; i <= pwdlen; i++) {
                op = ps.charAt(pwdlen - i) + (us.charAt(i - 1) % 5);
                op = 126 - op + 32;
                openPwd = openPwd + (char) op;
            }

            return (openPwd);
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static void main(String[] args) {
        //http://192.168.0.17/kshServices/loginAction/login?params=*****

        // 登录用户名称&单位（12位）&时间搓（yyyy-MM-dd HH:mm:ss）&系统名称
        
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String str = "zwr&441700000000&"+now+"&CJZD";
        
        String secret = Test1.encrypt(str, "110");
        System.out.println("secret: "+secret);
        
        String param = Base64.getEncoder().encodeToString(secret.getBytes());
        System.out.println("encode: "+param);

        System.out.println("http://127.0.0.1/kshServices/loginAction/login?params="+param);
        System.out.println("http://192.168.0.168/kshServices/loginAction/login?params="+param);
        
//        Base64.getDecoder().decode(new StringBuffer(param));
        
        
//        try {
//            String param = URLEncoder.encode(secret, "GBK");
//            Base64.getEncoder().encode(param.getBytes());
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            String encode = base64Encoder.encode();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        
    }

}
