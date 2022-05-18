package zgh.tool;

import java.io.File;

public class RenameFile {
    
    public static void main(String[] args) {
        new RenameFile().handle();
    }

    private void handle() {
        String strDir = "C:/zgh/photo/raw/2";
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