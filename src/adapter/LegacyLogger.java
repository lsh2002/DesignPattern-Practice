package adapter;

/**
 * 被适配者 Adaptee
 * 
 * 遗留的，不想修改的代码
 */
public class LegacyLogger {

    /**
     * 
     * @param level 1代表信息，2代表错误
     * @param text
     */
    public void log(int level, String text) {
        if (level == 1) {
            System.out.println("[LEGACY INFO] " + text);
        } else if (level == 2) {
            System.out.println("[LEGACY ERROR] " + text);
        } else {
            System.out.println("[LEGACY UNKNOWN] " + text);
        }
    }
}
