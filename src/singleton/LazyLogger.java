
package singleton;

/**
 * 懒汉式单例
 * 
 * 优点：
 * 1. 懒加载，需要时才加载
 * 2. 线程安全
 */
public class LazyLogger {

    private static volatile LazyLogger instance;

    private LazyLogger() {
    }

    public static LazyLogger getInstance() {
        if (instance == null) {
            synchronized (LazyLogger.class) {
                if (instance == null) {
                    instance = new LazyLogger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        String format = String.format("[LOG]%s", message);
        System.out.println(format);
    }
}