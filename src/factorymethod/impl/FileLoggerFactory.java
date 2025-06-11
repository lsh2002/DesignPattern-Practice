package factorymethod.impl;

import factorymethod.Logger;
import factorymethod.LoggerFactory;

/**
 * 实现工厂接口的消息写入文件类
 */
public class FileLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
        return new FileLogger();
    }

}
