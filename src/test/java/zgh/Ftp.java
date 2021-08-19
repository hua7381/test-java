package zgh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Iterator;

import org.junit.Test;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;

@SuppressWarnings("restriction")
public class Ftp {
    public static String ip = "111.230.39.172";
    public static int port = 21;
    public static String user = "ftpbai";
    public static String password = "20171225";
    
    /**
     * 遍历文件
     */
    @Test
    public void fn1() throws Exception {
        FtpClient ftpClient = getConn();
        try {
            //遍历目录下的所有文件(或目录)
            Iterator<FtpDirEntry> it = ftpClient.listFiles("");
            while(it.hasNext()) {
                System.out.println(it.next());
            }
        } finally {
            closeConn(ftpClient);
        }
        System.out.println("ok");
    }
    
    /**
     * 上传文件
     */
    @Test
    public void fn2() throws Exception {
        FtpClient ftpClient = getConn();
        try {
            OutputStream os = null;
            FileInputStream is = null;
            try {
                ftpClient.changeDirectory("xml_in");
                os = ftpClient.putFileStream("tar3.xml", true);
                is = new FileInputStream(new File("c:/zgh/test/src.xml"));
                byte[] bytes = new byte[1024];
                int c;
                int n = 1;
                while ((c = is.read(bytes)) != -1) {
                    os.write(bytes, 0, c);
                    if (n > 100) {
                        break;
                    }
                }
            } finally {
                if (os != null) os.close();
                if (is != null) is.close();
            }
        } finally {
            closeConn(ftpClient);
        }
        System.out.println("ok");
    }
    
    /**
     * 删除文件
     */
    @Test
    public void fn3() {
        FtpClient ftpClient = getConn();
        try {
            ftpClient.deleteFile("xml_in/tar3.xml");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(ftpClient);
        }
        System.out.println("ok");
    }
    
    private FtpClient getConn() {
        FtpClient ftpClient = FtpClient.create();
        try {
            //init
            ftpClient.connect(new InetSocketAddress(ip, port));
            ftpClient.login(user, password.toCharArray());
            ftpClient.setBinaryType();// 用2进制上传、下载
        } catch(Exception e) {
            
        } finally {
            
        }
        return ftpClient;
    }
    
    private void closeConn(FtpClient ftpClient) {
        if (ftpClient != null) {
            try {
                ftpClient.close();
            } catch (IOException e) {
            }
        }
    }

}
