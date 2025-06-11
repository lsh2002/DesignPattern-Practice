package factorymethod.impl;

import factorymethod.Logger;
import factorymethod.LoggerFactory;

/**
 * 实现工厂接口的打印消息类
 */
public class ConsoleLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
        return new ConsoleLogger();
    }

}
