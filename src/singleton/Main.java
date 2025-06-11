package singleton;

public class Main {

    public static void main(String[] args) {

        Logger instance1 = Logger.getInstance();
        Logger instance2 = Logger.getInstance();

        instance1.log("消息1");
        instance2.log("消息2");

        // 测试饿汉式单例，结果为true，说明是同一个实例
        System.out.println(instance1 == instance2);

        // 测试懒汉式单例的线程安全
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println(LazyLogger.getInstance().hashCode());
                }
            };

            new Thread(runnable).start();

        }
    }
}
