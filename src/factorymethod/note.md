# 设计模式：工厂方法模式 (Factory Method)

工厂方法模式定义了一个用于创建对象的接口，但让子类决定实例化哪一个类。它将类的实例化延迟到其子类。

这是一种**创建型**设计模式，其核心在于将**对象的创建**与**对象的使用**分离开来。

## 核心思想与解决的问题

*   **问题**: 当一个类不知道它所必须创建的对象的类时，或者当一个类希望由其子类来指定它所创建的对象时，或者当类将创建对象的职责委托给多个帮助子类中的某一个，并且你希望将关于哪一个帮助子类是代理者的信息局部化时。
*   **解决方案**: 将创建产品的代码从使用产品的代码中分离出来，封装到一个独立的"工厂"类中。客户端不再直接 `new` 具体产品，而是向工厂请求一个产品。

## 优点

1.  **解耦 (Decoupling)**: 客户端代码只依赖于抽象的`产品接口`和`工厂接口`，而不依赖于任何具体产品的实现。这使得代码更灵活。
2.  **符合开闭原则 (Open/Closed Principle)**: 当需要引入新产品时，无需修改现有客户端代码或工厂代码。只需添加一个新的`具体产品`类和一个新的`具体工厂`类即可，完全符合"对扩展开放，对修改关闭"的原则。
3.  **单一职责原则 (Single Responsibility Principle)**: 你可以将产品创建代码集中到代码中的一个位置，从而使得代码更容易维护。

## 模式中的角色

1.  **产品接口 (Product)**: 定义了工厂方法所创建的对象的接口。 (`Logger`)
2.  **具体产品 (Concrete Product)**: 实现产品接口。 (`ConsoleLogger`, `FileLogger`)
3.  **工厂接口 (Creator / Factory)**: 声明工厂方法，该方法返回一个产品类型的对象。 (`LoggerFactory`)
4.  **具体工厂 (Concrete Creator / Concrete Factory)**: 重写工厂方法以返回一个具体产品的实例。 (`ConsoleLoggerFactory`, `FileLoggerFactory`)

## 代码示例

### 1. 抽象层 (接口)

**产品接口: Logger.java**
```java
package factorymethod;

public interface Logger {
    void log(String message);
}
```

**工厂接口: LoggerFactory.java**
```java
package factorymethod;

public interface LoggerFactory {
    Logger createLogger();
}
```

### 2. 实现层 (具体类)

**具体产品: ConsoleLogger.java**
```java
package factorymethod.impl;
import factorymethod.Logger;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("Log to Console: " + message);
    }
}
```

**具体产品: FileLogger.java**
```java
package factorymethod.impl;
import factorymethod.Logger;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write("Log to File: " + message + "\\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**具体工厂: ConsoleLoggerFactory.java**
```java
package factorymethod.impl;
import factorymethod.Logger;
import factorymethod.LoggerFactory;

public class ConsoleLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        return new ConsoleLogger();
    }
}
```

**具体工厂: FileLoggerFactory.java**
```java
package factorymethod.impl;
import factorymethod.Logger;
import factorymethod.LoggerFactory;

public class FileLoggerFactory implements LoggerFactory {
    @Override

    public Logger createLogger() {
        return new FileLogger();
    }
}
```

### 3. 客户端 (使用)

**Main.java**
```java
package factorymethod;
import factorymethod.impl.ConsoleLoggerFactory;
import factorymethod.impl.FileLoggerFactory;

public class Main {
    public static void main(String[] args) {
        // 使用控制台日志工厂
        LoggerFactory consoleFactory = new ConsoleLoggerFactory();
        Logger consoleLogger = consoleFactory.createLogger();
        consoleLogger.log("This is a console log message.");

        // 切换到文件日志工厂
        LoggerFactory fileFactory = new FileLoggerFactory();
        Logger fileLogger = fileFactory.createLogger();
        fileLogger.log("This is a file log message.");
    }
}
```

---

## 应用场景深度解析：什么时候真正需要工厂方法？

学习设计模式，最关键的不是背下它的结构，而是要深刻理解："**我到底在什么场景下，才会真正需要用它？**"

### 场景：一个支持多种导出功能的报表系统

假设你正在开发一个允许用户将报表**导出**成不同格式（`PDF`, `CSV`等）的系统。

#### 版本 1.0：最直接的实现 (没有工厂)

客户端代码直接使用 `if-else` 来判断并创建所需的对象。

```java
public class ReportController {
    public void onExportButtonClick(String exportType) {
        Exportable exporter;
        // 客户端与具体实现类紧密耦合
        if ("pdf".equalsIgnoreCase(exportType)) {
            exporter = new PdfExporter(); 
        } else if ("csv".equalsIgnoreCase(exportType)) {
            exporter = new CsvExporter();
        } // ...
        exporter.export("some data...");
    }
}
```
**问题**: 如果要增加新的导出格式（如 `XML`），就必须修改 `ReportController` 这个已经存在的类，违反了"开闭原则"。如果多处都有这样的创建逻辑，维护将成为一场灾难。

#### 版本 2.0：工厂方法模式重构

我们将创建过程从客户端代码中分离出去，封装到各个具体的工厂里。

**工厂接口与实现:**
```java
// 工厂接口
interface ExporterFactory {
    Exportable createExporter();
}
// 具体工厂
class PdfExporterFactory implements ExporterFactory {
    public Exportable createExporter() { return new PdfExporter(); }
}
class CsvExporterFactory implements ExporterFactory {
    public Exportable createExporter() { return new CsvExporter(); }
}
```

**重构后的客户端:**
```java
public class ReportController {
    private ExporterFactory factory; 

    public ReportController(ExporterFactory factory) {
        this.factory = factory;
    }

    public void onExportButtonClick() {
        // 完全解耦！客户端只与工厂接口交互，不关心具体产品。
        Exportable exporter = factory.createExporter(); 
        exporter.export("some data...");
    }
}
```
**好处**: 现在再增加 `XML` 导出功能时，我们只需要新增 `XmlExporter` 和 `XmlExporterFactory` 两个类，**完全不需要改动** `ReportController` 的代码。系统的可扩展性和可维护性大大提高。

### "经验法则"

当你下次写代码时，可以问自己一个问题：

> **"我这里正在 `new` 一个对象。在未来，我需要 `new` 的对象的种类会不会增加或改变？我希望在不改动当前业务逻辑（这个类）的前提下，就能支持这些新的种类吗？"**

如果答案是"是"，那么工厂方法模式就是一个非常值得考虑的优秀方案。

---

## 核心本质：对 `new` 关键字的封装

可以这样理解，工厂方法模式的本质，就是对 `new` 这个关键字的一种封装和管理。

1.  **直接 `new`**: 当你写 `new MyObject()` 时，你的代码就和 `MyObject` 这个具体的类"绑死"了。这就像你在家做饭，你决定了今天就用"土豆"这个具体的食材，你的菜谱（代码）就跟"土豆"分不开了。

2.  **使用工厂方法**: 你不对厨房说"给我一个土豆"，而是对一个"食材工厂"说"给我一个今天该用的蔬菜"。
    *   这个"食材工厂"可能是"夏季蔬菜工厂"，它会给你一个"黄瓜"。
    *   也可能是"冬季蔬菜工厂"，它会给你一个"白菜"。

    你的菜谱（客户端代码）根本不关心拿到的是黄瓜还是白菜，它只知道"我拿到了一个蔬菜（产品接口），我可以对它进行'炒'这个操作（接口方法）"。

所以，工厂方法模式的触发点，就是你在代码中写下 `new` 的那一刻。
