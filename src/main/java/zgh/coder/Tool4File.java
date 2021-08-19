package zgh.coder;

import java.io.File;

/**
 * Tool4File
 */
public class Tool4File {

  private long num = 0L;

    public void del(File file) {
        if (file.isDirectory() && file.listFiles().length > 0) {
            for (File sub : file.listFiles()) {
        del(sub);
            }
        }
        try {
      file.delete();
      num ++;
      if (num % 1000 == 0) {
        System.out.println("delted: "+num+" files, now: "+file.getPath());
      }
        } catch (Exception e) {
            System.out.println(e.getMessage());
    }
  }

  public long getNum() {
    return num;
  }
  
}