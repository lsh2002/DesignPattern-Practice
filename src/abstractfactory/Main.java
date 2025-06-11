package abstractfactory;

import abstractfactory.impl.MacFactory;
import abstractfactory.impl.WindowsFactory;

public class Main {
    public static void main(String[] args) {
        GUIFactory factory = new WindowsFactory();
        Button button = factory.createButton();
        button.render();
        TextField textField = factory.createTextField();
        textField.render();

        factory = new MacFactory();
        button = factory.createButton();
        button.render();
        textField = factory.createTextField();
        textField.render();
    }
}
