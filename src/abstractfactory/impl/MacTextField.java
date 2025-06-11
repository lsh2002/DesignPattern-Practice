package abstractfactory.impl;

import abstractfactory.TextField;

/**
 * mac风格文本框
 */
public class MacTextField implements TextField {

    @Override
    public void render() {
        System.out.println("mac风格文本框");
    }
}