package adapter;

/**
 * 适配器 Adapter
 */
public class LoggerAdapter implements NewLogger {

    private LegacyLogger legacyLogger;

    public LoggerAdapter(LegacyLogger legacyLogger) {
        this.legacyLogger = legacyLogger;
    }

    @Override
    public void error(String message) {
        legacyLogger.log(2, message);
    }

    @Override
    public void info(String message) {
        legacyLogger.log(1, message);
    }
}
