package singleton;

/**
 * 饿汉式单例
 * 
 * 
 * 缺点：
 * 1. 加载慢
 * 2. 浪费内存
 */
public class Logger {

    private static final Logger logger = new Logger();

    private Logger() {
    }

    public static Logger getInstance() {
        return logger;
    }

    public void log(String message) {
        String log = String.format("[LOG]%s", message);
        System.out.println(log);
    }
}
