package zgh;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class RunBat {

    @Test
    public void test1() {
        System.out.println("hello");
        // callCmd("c:/t/ic-coop-api.bat");
        callCmd("c:/t/test.bat");
    }

    public static void callCmd(String locationCmd) {
        try {
            Process child = Runtime.getRuntime().exec(locationCmd);
            InputStream in = child.getInputStream();
            int c;

            StringBuffer sb = new StringBuffer();
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
            in.close();

            System.out.println(sb);

            try {
                child.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}