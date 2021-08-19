package zgh.coder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatCode {
    String PATH = "C:/zgh/code/ms-login2-api";

	public static void main(String[] args) {
        new FormatCode().process();
    }

    private void process() {
        int num = format(new File(PATH));
        System.out.println("finished, handled " + num + " files");
    }

    private static Set<String> set = new HashSet<String>();
    static {
        set.add("java");
        set.add("xml");
        set.add("vue");
        set.add("js");
    }

    private int format(File file) {
        int num = 0;
        if (file.isDirectory()) {
            for(File f : file.listFiles()) {
                num += format(f);
            }
        } else {
            handleOne(file);
            num = 1;
        }
        return num;
    }

    private void handleOne(File file) {
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if(set.contains(ext)) {
            formatOne(file, ext);
        }
    }

    private void formatOne(File file, String ext) {
        String content = read(file);

        content = content.replace(",  ", ", ");
        content = replaceByReg(content, "[^ ][ ],", " ,", ","); // xxx ,
        content = replaceByReg(content, ",[A-Za-z0-9\\(]", ",", ", ");// ,xxx

        content = replaceByReg(content, "[^ \"] \\)", " )", ")"); // xxx )

        content = content.replace("if(", "if (");
        content = content.replace("for(", "for (");
        content = content.replace("){", ") {");
        content = content.replace("}else", "} else");
        content = content.replace("else{", "else {");
        if ("java".equals(ext)) {
            content = content.replace("	", "    ");
        } else if("vue".equals(ext) || "js".equals(ext)) {
            content = content.replace("	", "  ");
        }
        write(file, content);
    }

    private String replaceByReg(String content, String reg, String replaceFrom, String replaceTo) {
        Matcher matcher = Pattern.compile(reg).matcher(content);

        Set<String> set = new HashSet<String>();
        while(matcher.find()) {
            String str = matcher.group();
            if(!set.contains(str)) {
                set.add(str);
            }
        }

        for(String str : set) {
            content = content.replace(str, str.replace(replaceFrom, replaceTo));
        }
        
        return content;
    }

    private String read(File file) {
        FileInputStream fis = null;
        Long len = file.length();
        byte[] bytes = new byte[len.intValue()];
        try {
            fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();
            return new String(bytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

	public void write(File file, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
			writer.write(content);
		} catch(Exception e) {
            e.printStackTrace();
        } finally {
			try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
	}

}