package abstractfactory.impl;

import abstractfactory.Button;

/**
 * windows风格按钮
 */
public class WindowsButton implements Button{

    @Override
    public void render() {
        System.out.println("windows风格按钮");
    }
}