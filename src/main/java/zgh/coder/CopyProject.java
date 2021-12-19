package zgh.coder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * CopyCode
 * 拷贝java工程目录, 仅包含src与pom.xml
 */
public class CopyProject {

    public static void main(String[] args) {
        CopyProject obj = new CopyProject();

        String tar = "c:/t/copy";
        new Tool4File().del(new File(tar));

        // 示例
        //obj.copy("c:/zgh/code/_case/ms-case-base", tar+"/_case/ms-case-base");
        // obj.copy("c:/zgh/code/ic-resource", tar+"/ic-resource");




        obj.copy("c:/zgh/code/_ic_api/ic-base", tar+"/_ic_api/ic-base");
        
        obj.copy("c:/zgh/code/_ic_api/ic-home-api", tar+"/_ic_api/ic-home-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-home-ui", tar+"/_ic_ui/ic-home-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-coop-api", tar+"/_ic_api/ic-coop-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-coop-ui", tar+"/_ic_ui/ic-coop-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-focus-api", tar+"/_ic_api/ic-focus-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-focus-ui", tar+"/_ic_ui/ic-focus-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-refine-api", tar+"/_ic_api/ic-refine-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-refine-ui", tar+"/_ic_ui/ic-refine-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-situation-api", tar+"/_ic_api/ic-situation-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-situation-ui", tar+"/_ic_ui/ic-situation-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-report-api", tar+"/_ic_api/ic-report-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-report-ui", tar+"/_ic_ui/ic-report-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-analyse-api", tar+"/_ic_api/ic-analyse-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-analyse-ui", tar+"/_ic_ui/ic-analyse-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-doc-api", tar+"/_ic_api/ic-doc-api");
        obj.copy("c:/zgh/code/_ic_ui/ic-doc-ui", tar+"/_ic_ui/ic-doc-ui");

        obj.copy("c:/zgh/code/_ic_api/ic-outerdata", tar+"/_ic_api/ic-outerdata");
        obj.copy("c:/zgh/code/_ic_api/ic-stat", tar+"/_ic_api/ic-stat");




        // obj.copy("c:/zgh/code/ic-report-m-api", tar+"/ic-report-m-api");
        // obj.copy("c:/zgh/code/ic-report-m-ui", tar+"/ic-report-m-ui");

        // obj.copy("c:/zgh/code/ic-focus-m-ui", tar+"/ic-focus-m-ui");

        // obj.copy("c:/zgh/code/ms-login2-api", tar+"/ms-login2-api");
        // obj.copy("c:/zgh/code/ms-login2-ui", tar+"/ms-login2-ui");
        // obj.copy("c:/zgh/code/ms-netty", tar+"/ms-netty");
        // obj.copy("c:/zgh/code/ms-file", tar+"/ms-file");
        
        // obj.copy("c:/zgh/code/kc-cjzd", tar+"/kc-cjzd");
        // obj.copy("c:/zgh/code/_case/ms-case-base", tar+"/ms-case-base");
        // obj.copy("c:/zgh/code/_case/ms-caseDispatch", tar+"/ms-caseDispatch");
        // obj.copy("c:/zgh/code/_case/ms-caseNotify", tar+"/ms-caseNotify");
        // obj.copy("c:/zgh/code/_case/ms-caseQuery", tar+"/ms-caseQuery");
        // obj.copy("c:/zgh/code/_case/ms-caseReply", tar+"/ms-caseReply");
    }

    private void copy(String sourceDir, String targetDir) {
        System.out.println("copy: "+sourceDir+" -> \n"+targetDir);
        if (!new File(sourceDir).exists()) {
            System.out.println("项目不存在: " + sourceDir);
            return;
        }
        (new File(targetDir)).mkdirs();

        try {
            copyDir(sourceDir+"/src", targetDir+"/src");
            copyDir(sourceDir+"/public", targetDir+"/public");

            copyFile(new File(sourceDir+"/pom.xml"), new File(targetDir+"/pom.xml"));
            copyFile(new File(sourceDir+"/README.md"), new File(targetDir+"/README.md"));
            
            copyFile(new File(sourceDir+"/babel.config.js"), new File(targetDir+"/babel.config.js"));
            copyFile(new File(sourceDir+"/package.json"), new File(targetDir+"/package.json"));
            copyFile(new File(sourceDir+"/webpack.config.js"), new File(targetDir+"/webpack.config.js"));
            copyFile(new File(sourceDir+"/vue.config.js"), new File(targetDir+"/vue.config.js"));
            copyFile(new File(sourceDir+"/yarn.lock"), new File(targetDir+"/yarn.lock"));

            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 复制文件夹
    private void copyDir(String sourceDir, String targetDir) throws IOException {
        if (! new File(sourceDir).exists()) {
            return;
        }

        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] files = (new File(sourceDir)).listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // 源文件
                File sourceFile = files[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + files[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (files[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + files[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + files[i].getName();
                copyDir(dir1, dir2);
            }
        }
    }

    private void copyFile(File sourceFile, File targetFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }

        // 刷新此缓冲的输出流
        outBuff.flush();

        // 关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

}