package abstractfactory.impl;

import abstractfactory.Button;

/**
 * mac风格按钮
 */
public class MacButton implements Button{

    @Override
    public void render() {
        System.out.println("mac风格按钮");
    }
}
