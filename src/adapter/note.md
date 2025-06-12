# 设计模式：适配器模式 (Adapter Pattern)

### 💡 核心理解：接口不对，需要"转接头"！

想象你刚买了一个最新、最强大的耳机（Type-C 插头），你想把它插到你珍藏多年的老式MP3播放器（只有 3.5mm 耳机孔）上。

*   **你的新耳机 (被适配者/Adaptee)**：功能强大，但接口是 Type-C。
*   **你的老MP3 (目标接口/Target)**：稳定工作，但只认 3.5mm 接口。
*   **问题**：接口不兼容，你不能直接用。
*   **你的核心需求**：你既不想改耳机，也不想改MP3，只希望它们**现在就能一起工作**。

那一刻，你脑海里立刻闪过的"Type-C 转 3.5mm 的转接头"，就是**适配器模式**！

### 什么时候你"一下子"就会想到它？

当你写代码时，脑子里浮现出以下场景，适配器模式就该跳出来了：

1.  **"我有两个类，它们的功能都很好，但是它们的接口对不上！"**
    *   一个类提供 `processData(String input)`，另一个类期望 `handleInput(byte[] data)`。
    *   你有一个旧的第三方库，它有一个 `OldApi.doSomething()` 方法，但你的新系统只认识 `NewApi.execute()`。

2.  **"我不能或者不想修改这两个现有的类！"**
    *   旧的类可能是遗留代码，修改它风险高、成本大。
    *   新的接口可能是系统标准，不能随意改动。
    *   第三方库的代码你根本没权限改。

当你面临"接口不兼容"和"不能/不想修改现有代码"这两个条件同时满足时，适配器模式几乎就是唯一的优雅解决方案。

它让你在不侵入现有代码结构的前提下，平滑地整合不同接口的功能，实现"即插即用"的效果。

## 核心思想与解决的问题

*   **问题**: 你有一个现有的、功能强大的类（**被适配者 Adaptee**），但它的接口（方法名、参数等）与你当前系统所期望的接口（**目标 Target**）不匹配。你不想或不能修改现有类。
*   **解决方案**: 创建一个**适配器 (Adapter)** 类。这个适配器类实现你系统所需的`Target`接口，但在内部，它会去调用那个`Adaptee`类的方法来完成实际工作。这样，客户端代码就可以通过标准的`Target`接口来使用`Adaptee`了。

### 具体应用场景：统一第三方支付接口

想象你的电商平台需要集成多种第三方支付服务（如支付宝、微信支付、PayPal）。每个支付服务都有自己独特的API接口（方法名、参数顺序等）。

*   **问题**: 如果你的业务逻辑直接调用这些差异巨大的第三方API，那么每增加一个新的支付方式，或某个支付方式的API发生变化，你都需要修改大量的业务代码，这将导致代码混乱且难以维护。

*   **解决方案**: 定义一个统一的**目标接口 (Target)**，例如 `PaymentGateway`，它包含 `processPayment(amount, currency)` 等方法。然后，为每个第三方支付服务创建一个**适配器 (Adapter)**。例如，`AlipayAdapter` 实现 `PaymentGateway` 接口，并在其内部调用支付宝的API；`WechatPayAdapter` 同样实现 `PaymentGateway` 接口，并在内部调用微信支付的API。

    这样，你的电商平台业务逻辑就只与统一的 `PaymentGateway` 接口交互，完全屏蔽了底层支付服务的具体实现细节。无论底层有多少种支付方式，或者它们如何变化，你的核心业务代码都无需改动，只需增加或修改对应的适配器即可。

## 模式中的角色

1.  **目标接口 (Target)**: 客户端所期望使用的接口。 (`NewLogger`)
2.  **被适配者 (Adaptee)**: 已有的、功能符合但接口不兼容的类。 (`LegacyLogger`)
3.  **适配器 (Adapter)**: 实现`目标接口`，并持有一个`被适配者`的实例。它负责将`目标接口`的调用转换成`被适配者`可以理解的调用。 (`LoggerAdapter`)
4.  **客户端 (Client)**: 与`目标接口`进行交互，通过`目标接口`使用`被适配者`的功能。

## 代码示例

### 1. 目标接口: NewLogger.java

```java
package adapter;

public interface NewLogger {
    void info(String message);
    void error(String message);
}
```

### 2. 被适配者: LegacyLogger.java

```java
package adapter;

public class LegacyLogger {
    public void log(int level, String text) {
        if (level == 1) {
            System.out.println("[LEGACY INFO] " + text);
        } else if (level == 2) {
            System.out.println("[LEGACY ERROR] " + text);
        } else {
            System.out.println("[LEGACY UNKNOWN] " + text);
        }
    }
}
```

### 3. 适配器: LoggerAdapter.java

```java
package adapter;

public class LoggerAdapter implements NewLogger {
    private LegacyLogger legacyLogger;

    public LoggerAdapter(LegacyLogger legacyLogger) {
        this.legacyLogger = legacyLogger;
    }

    @Override
    public void info(String message) {
        legacyLogger.log(1, message); // 适配调用
    }

    @Override
    public void error(String message) {
        legacyLogger.log(2, message); // 适配调用
    }
}
```

### 4. 客户端: Main.java

```java
package adapter;

public class Main {
    public static void main(String[] args) {
        // 创建被适配者实例
        LegacyLogger legacyLogger = new LegacyLogger();
        
        // 创建适配器，并将其作为目标接口使用
        NewLogger newLogger = new LoggerAdapter(legacyLogger);

        // 客户端通过目标接口使用功能，无需关心底层实现细节
        newLogger.info("这是适配器模式的信息日志。");
        newLogger.error("这是适配器模式的错误日志。");
    }
}
``` 