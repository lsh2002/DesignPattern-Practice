package adapter;

public class Main {
    public static void main(String[] args) {

        LegacyLogger legacyLogger = new LegacyLogger();
        NewLogger loggerAdapter = new LoggerAdapter(legacyLogger);

        loggerAdapter.error("这是适配器模式的错误日志");
        loggerAdapter.info("这是适配器模式的信息日志");

    }
}
