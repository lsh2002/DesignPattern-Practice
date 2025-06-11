package abstractfactory.impl;

import abstractfactory.Button;
import abstractfactory.GUIFactory;
import abstractfactory.TextField;

/**
 * 具体工厂：mac
 */
public class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public TextField createTextField() {
        return new MacTextField();
    }
    
}
