package zgh.tool;

import java.io.File;

public class RenameFile {
    
    public static void main(String[] args) {
        new RenameFile().handle();
    }

    private void handle() {
        String strDir = "c:/t/rename";
        String prefix = "";
        String suffix = ".jpg";
        String replace = ".jfif";
        String replaceTo = "";

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