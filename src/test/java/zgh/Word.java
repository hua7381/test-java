package zgh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * word
 * @author zhangguihua(hua7381@163.com)
 * date: 2018年4月8日
 */
public class Word {
    
    /**
     * 以HTML格式导出word文件
     * @throws Exception
     */
    @Test
    public void fn1() throws Exception {
        
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            in = new BufferedReader(new FileReader(new File(Tool.getClasspath()+"word.html")));
            String now = new SimpleDateFormat("yyMMdd_HH_mm_ss").format(new Date());
            out = new BufferedWriter(new FileWriter(new File("C:/test/"+now+".doc")));
                    
            String line = null;
            while((line = in.readLine()) != null) {
                out.write(line);
            }
            
        } finally {
            if (out!=null) out.close();
            if (in!=null) in.close();
        }
    }
    
}
