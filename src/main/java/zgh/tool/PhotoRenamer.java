package zgh.tool;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import org.apache.commons.lang3.StringUtils;

/**
 * 照片批处理：按拍摄时间改名
 */
public class PhotoRenamer {
    static String srcDir = "c:/t/from";
    static String destDir = "c:/t/to";

    public static void main(String[] args) {
        new File(destDir).mkdirs();
        File dir = new File(srcDir);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (!file.isDirectory()) {
                    String name = file.getName();
                    if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".JPG") || name.endsWith(".JPEG")) {
                        handleOne(file);
                    }
                }
            }
        } else {
            System.out.println("非目录");
        }
        System.out.println("finish");
    }

    private static void handleOne(File file) {
        Map<String, String> info = getInfo(file);
        // System.out.println(JSON.toJSONString(info));
        String shotTime = info.get("shotTime");
        if (StringUtils.isNotEmpty(shotTime)) {
            file.renameTo(new File(destDir + "/" + calcName(shotTime) + ".jpg"));
        }
    }

    static SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    static SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy_MMdd_HHmmss");
    // 去重后缀
    static Map<String, Integer> suffixs = new HashMap<String, Integer>();
    private static String calcName(String shotTime) {
        try {
            String res = fmt2.format(fmt1.parse(shotTime));

            if (!suffixs.containsKey(res)) {
                suffixs.put(res, 0);
            } else {
                suffixs.put(res, suffixs.get(res) + 1);
            }
            String suffix = suffixs.get(res) == 0 ? "" : "_" + suffixs.get(res);

            return res + suffix;
        } catch (ParseException e) {
            return null;
        }

    }

    private static Map<String, String> getInfo(File file) {
        Map<String, String> tags = new HashMap<String, String>();
        tags.put("Image Width", "width");
        tags.put("Image Height", "height");
        tags.put("Model", "model");
        tags.put("Exposure Time", "exposureTime");
        tags.put("F-Number", "fNumber");
        tags.put("Date/Time Original", "shotTime");
        tags.put("Flash", "flash");
        tags.put("Focal Length", "focalLength");
        try {
            Metadata metadata = JpegMetadataReader.readMetadata(file);
            Map<String, String> info = new HashMap<String, String>();
            for (Directory one : metadata.getDirectories()) {
                for (Tag tag : one.getTags()) {
                    // String aa = tag.getDirectoryName();
                    String bb = tag.getTagName();
                    String cc = tag.getDescription();
                    if (bb != null && !bb.contains("Unknown") && cc != null && !cc.contains("Unknown")) {
                        if (tags.containsKey(bb)) {
                            // System.out.println(String.format("%s, %s, %s", aa, bb, cc));
                            info.put(tags.get(bb), cc);
                        }
                    }
                }
            }
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}