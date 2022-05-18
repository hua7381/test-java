package zgh.tool;

import java.io.File;
import java.io.IOException;

import zgh.util.FileUtil;

/**
 * 根据jpg的名称，挑选相应的raw
 */
public class CopyRawByJpg {

  private static String JPG_DIR = "C:/t";
  private static String TAR_DIR = "C:/t";
  private static String RAW_DIR = "c:/zgh/photo/camera";

  public static void main(String[] args) {
    System.out.println("start");
    new CopyRawByJpg().starts();
    System.out.println("finish");
  }

  public void starts() {
    for (File one : new File(JPG_DIR).listFiles()) {
      copyOne(one.getName());
    }
  }

  private void copyOne(String name) {
    if (name.endsWith("JPG")) {
      String newName = name.replace("JPG", "CR3");
      File raw = new File(RAW_DIR + "/" + newName);
      File tar = new File(TAR_DIR + "/" + newName);
      if (raw.exists() && !tar.exists()) {
        System.out.println("copy to " + tar.getPath());
        FileUtil.copyFile(raw, tar);
        System.out.println("ok");
      }
    }
  }

}