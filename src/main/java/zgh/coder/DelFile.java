package zgh.coder;

import java.io.File;

/**
 * DelFile
 */
public class DelFile {

  public static void main(String[] args) {
    Tool4File tool = new Tool4File();
    tool.del(new File("c:/zgh/code/test"));
    System.out.println("finished, delelted "+tool.getNum()+" files");
  }

}