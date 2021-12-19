package zgh;

public class Tool {

    public static String getClasspath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }
}
