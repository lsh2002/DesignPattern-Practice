package abstractfactory.impl;

import abstractfactory.Button;
import abstractfactory.GUIFactory;
import abstractfactory.TextField;

/**
 * 具体工厂：windows
 */
public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }
    
}
