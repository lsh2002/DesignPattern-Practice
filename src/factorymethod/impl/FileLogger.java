package factorymethod.impl;

import java.io.FileWriter;
import java.io.IOException;

import factorymethod.Logger;

/**
 * 消息写入本地文件
 */
public class FileLogger implements Logger {

    @Override
    public void log(String message) {
        // 写入本地文件 log.txt
        try {
            FileWriter fileWriter = new FileWriter("log.txt", true);
            fileWriter.write(message);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
