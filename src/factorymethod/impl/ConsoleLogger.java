package factorymethod.impl;

import factorymethod.Logger;

/**
 * 打印实现类
 */
public class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println(message);
    }
    
}
