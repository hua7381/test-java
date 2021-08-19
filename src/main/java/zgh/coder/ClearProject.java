package zgh.coder;

import java.io.File;

/**
 * 清除工程目录
 * 可指定工程目录本身，或其上一级
 * @author zhangguihua
 *
 */
public class ClearProject {
    // 要清理的根目录
    private static final String PATH = "C:/zgh/code/github";
//    private static final String PATH = "C:/zgh/temp/7a2/_case";
    // 要删除的目录
    private static final String[] DEL_ARR = {
//            "src", "pom.xml",
//            ".git", 
//            ".gitignore", "README.md", 
            "target", ".classpath", ".project", ".settings",
            "xxx"
            };
    
    private static int total = 0;
    private static int delNum = 0;
    
    public static void main(String[] args) {
        new ClearProject().clearProjects(PATH);
    }
    
    public void clearProjects(String path) {
        total = 0;
        delNum = 0;
        
        File dir = new File(path);
        
        clearProjectDicr(dir);
        for (File subDir : dir.listFiles()) {
            clearProjectDicr(subDir);
        }
        
        System.out.println("finished. total: "+total+", delNum: "+delNum);
    }
    
    private void clearProjectDicr(File dir) {
        if (dir.isDirectory()) {
            for (File subFile : dir.listFiles()) {
                checkOne(subFile);
            }
        }
    }

    private void checkOne(File sub) {
        for (String del : DEL_ARR) {
            if (sub.getName().equals(del)) {
                new Tool4File().del(sub);
            }
        }
    }
    
}
