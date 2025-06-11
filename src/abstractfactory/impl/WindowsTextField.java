package abstractfactory.impl;

import abstractfactory.TextField;

/**
 * windows风格文本框
 */
public class WindowsTextField implements TextField {

    @Override
    public void render() {
        System.out.println("windows风格文本框");
        
    }
}
