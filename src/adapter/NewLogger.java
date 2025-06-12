package adapter;

/**
 * 目标接口
 */
public interface NewLogger {
    
    void info(String message);

    void error(String message);
}
