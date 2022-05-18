package zgh.tool;

import java.io.File;

/**
 * 批量重命名
 */
public class RenameFile {
    
    public static void main(String[] args) {
        handle();
    }

    private static void handle() {
        // 目录
        String strDir = "C:/zgh/photo/raw/2";

        // 规则
        String prefix = "";
        String suffix = "";
        String replace = "2_IMG";
        String replaceTo = "IMG";

        File dir = new File(strDir);
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (!f.isDirectory()) {
                    f.renameTo(new File((strDir + "/" + prefix + f.getName() + suffix).replace(replace, replaceTo)));
                }
            }

        }
        System.out.println("finish");
    }

}