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

	public static void main(String[] args) {
        String path = "C:/zgh/code/my-compare/src";
        new FormatCode().format(new File(path));
        System.out.println("finished");
    }

    private static Set<String> set = new HashSet<String>();
    static {
        set.add("java");
        set.add("xml");
        set.add("vue");
        set.add("js");
    }

    private void format(File file) {
        if (file.isDirectory()) {
            for(File f : file.listFiles()) {
                format(f);
            }
        } else {
            handleOne(file);
        }
    }

    private void handleOne(File file) {
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if(set.contains(ext)) {
            formatOne(file);
        }
    }

    private void formatOne(File file) {
        String content = read(file);
        content = replace(content, "[^ ][ ],", " ,", ","); // xxx ,
        content = replace(content, ",[A-Za-z0-9\\(]", ",", ", ");// ,xxx
        content = replace(content, "[^ \"] \\)", " )", ")"); // xxx )
        content = content.replace(",  ", ", ");
        content = content.replace("if(", "if (");
        content = content.replace("for(", "for (");
        content = content.replace("( ", "(");
        content = content.replace("){", ") {");
        content = content.replace("}else", "} else");
        content = content.replace("else{", "else {");
        content = content.replace("=  ", "= ");
        write(file, content);
    }

    private String replace(String content, String reg, String replaceFrom, String replaceTo) {
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