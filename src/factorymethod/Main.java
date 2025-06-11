package factorymethod;

import factorymethod.impl.ConsoleLoggerFactory;
import factorymethod.impl.FileLoggerFactory;

public class Main {
    public static void main(String[] args) {
        
        ConsoleLoggerFactory consoleLoggerFactory = new ConsoleLoggerFactory();
        Logger logger = consoleLoggerFactory.createLogger();
        logger.log("111");

        FileLoggerFactory fileLoggerFactory = new FileLoggerFactory();
        Logger logger2 = fileLoggerFactory.createLogger();
        logger2.log("222");
    }
}
