public class Utils {
    public static String getExtension(String filename) {
        String[] split = filename.split(".");
        return split[split.length-1];
    }
}
