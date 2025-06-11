# 设计模式：抽象工厂模式 (Abstract Factory)

抽象工厂模式提供一个接口，用于创建一系列相关或相互依赖的对象，而无需指定它们具体的类。它处理的是"产品族"的创建问题。

这是一种**创建型**设计模式，可以看作是工厂方法模式的"升级版"。

## 核心思想与解决的问题

*   **问题**: 当你的系统需要创建**一整套**相互关联、需要一起工作的对象（一个"产品族"），并且你希望客户端代码与这些对象的具体实现解耦时。例如，一个UI库需要支持多种操作系统风格（Windows, macOS），每种风格都包含一套完整的控件（Button, TextField, Checkbox等），你希望保证创建的控件始终属于同一种风格，避免混搭。
*   **解决方案**: 定义一个抽象工厂接口，其中包含创建**所有**抽象产品的方法。然后，为每个产品族创建一个具体工厂类，该类实现抽象工厂接口，负责创建该产品族中的所有具体产品。

## 与工厂方法模式的区别

| 特性 | 工厂方法模式 (Factory Method) | 抽象工厂模式 (Abstract Factory) |
| :--- | :--- | :--- |
| **目的** | 创建**一个**产品 | 创建**一族**相关的产品 |
| **工厂接口**| 通常只有一个创建方法 `createProduct()` | 有多个创建方法 `createProductA()`, `createProductB()`... |
| **复杂性** | 相对简单 | 相对复杂，涉及多个产品层次 |
| **例子** | 一个日志工厂，可以创建文件日志记录器或控制台日志记录器 | 一个UI主题工厂，可以创建该主题下所有的控件（按钮、文本框等）|

## 模式中的角色

1.  **抽象产品 (Abstract Product)**: 为一类产品对象声明一个接口。 (`Button`, `TextField`)
2.  **具体产品 (Concrete Product)**: 定义一个将被相应的具体工厂创建的产品对象。 (`WindowsButton`, `MacButton`, `WindowsTextField`, `MacTextField`)
3.  **抽象工厂 (Abstract Factory)**: 声明一个创建抽象产品对象的操作接口。 (`GUIFactory`)
4.  **具体工厂 (Concrete Factory)**: 实现创建具体产品对象的操作。 (`WindowsFactory`, `MacFactory`)

## 代码示例

### 1. 抽象层 (接口)

**抽象产品: Button.java, TextField.java**
```java
// Button.java
public interface Button { void render(); }

// TextField.java
public interface TextField { void render(); }
```

**抽象工厂: GUIFactory.java**
```java
public interface GUIFactory {
    Button createButton();
    TextField createTextField();
}
```

### 2. 实现层 (具体类)

**具体产品:**
```java
// WindowsButton.java
public class WindowsButton implements Button {
    @Override
    public void render() { System.out.println("Rendering a button in Windows style."); }
}
// MacButton.java
public class MacButton implements Button {
    @Override
    public void render() { System.out.println("Rendering a button in macOS style."); }
}
// ... TextField 的实现类似 ...
```

**具体工厂:**
```java
// WindowsFactory.java
public class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() { return new WindowsButton(); }
    @Override
    public TextField createTextField() { return new WindowsTextField(); }
}

// MacFactory.java
public class MacFactory implements GUIFactory {
    @Override
    public Button createButton() { return new MacButton(); }
    @Override
    public TextField createTextField() { return new MacTextField(); }
}
```

### 3. 客户端 (使用)

**Main.java**
```java
public class Main {
    public static void main(String[] args) {
        // 决定使用 Windows 风格
        GUIFactory factory = new WindowsFactory();
        
        // 客户端只与抽象接口交互
        Button button = factory.createButton();
        TextField textField = factory.createTextField();
        
        button.render();
        textField.render();

        // 更换为 macOS 风格，只需更换工厂
        factory = new MacFactory();
        button = factory.createButton();
        textField = factory.createTextField();
        button.render();
        textField.render();
    }
}
```

---

## 深度对比：抽象工厂 vs 工厂方法

这个问题是创建型模式中最核心、也最容易混淆的部分。可以用一个简单的比喻来区分它们：

*   **工厂方法模式 (Factory Method) = 专一的生产线**
*   **抽象工厂模式 (Abstract Factory) = 组装套餐的全能工厂**

### 1. 目标不同：生产"一个"还是生产"一套"？

**工厂方法模式**只关心**一类产品**的创建。
它的目标是生产**单一**的产品。例如，`LoggerFactory` 的唯一目标就是创建出一个 `Logger`。

**抽象工厂模式**关心的是**一整套相互关联的产品（产品族）**。
它的目标是生产**一整套**的产品，并保证这一套产品是相互匹配的。例如，`GUIFactory` 不仅要创建 `Button`，还要创建 `TextField`，并保证它们都属于同一种UI风格。

**简单来说：**
*   当你只需要解耦**一个对象**的创建时，用**工厂方法**。
*   当你需要解耦**一整套对象**的创建，并保证它们之间相互兼容时，用**抽象工厂**。

### 2. 接口定义不同：一个方法还是多个方法？

这是从代码结构上最直观的区别：

**工厂方法模式**的工厂接口，通常**只有一个核心的创建方法**。
```java
public interface LoggerFactory {
    Logger createLogger(); // 目标单一
}
```

**抽象工厂模式**的工厂接口，必然包含**多个创建不同产品的方法**。
```java
public interface GUIFactory {
    Button createButton();     // 目标一
    TextField createTextField(); // 目标二
}
```

### 3. 复杂度和扩展方向不同

*   **工厂方法模式**在**产品维度**上扩展。当我们需要一个新的`Logger`（比如`DatabaseLogger`），我们只需要添加一个新的产品和一个新的工厂。
*   **抽象工厂模式**在**产品族维度**上扩展。当我们需要一个新的皮肤（比如`Linux`风格），我们只需要添加一个`LinuxFactory`，并实现该风格下的所有产品。
    *   **缺点**: 如果想给所有产品族都增加一个**新产品**（比如`Checkbox`），就必须修改抽象工厂接口，这会迫使所有具体工厂类都进行修改，违反了"开闭原则"。

### 总结法则

最简单的区分方法就是问自己：

> **"我的工厂是只生产一种东西，还是生产一系列需要配套使用的东西？"**

*   **只生产一种** -> **工厂方法模式**
*   **生产一系列** -> **抽象工厂模式** 