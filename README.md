# CashowMemoryMonitor

这是一个用来记录内存信息的库

### 简介

OutOfMemory 是 android 开发过程中无法避免的问题。当你把所有能优化的地方都优化过并自信满满地发布新版本，结果发现在第三方错误统计里还是满满的 OutOfMemory 崩溃日志的时候，你的内心是绝望的。

这个库解决不了上面的问题，但是（希望）能帮你尽快定位到内存开销较大的地方。

### 接入说明

1. 引入library库
```
compile 'com.cashow:cashowmemorymonitor:1.1'
```
2. 在任何你想要做记录的地方（例如 Activity 和 Fragment 的 onCreate() 和 onDestroy() ）加上 `MemoryLog.addLog(this)`。如果你的类名或者方法名混淆过了，请调用`MemoryLog.addLog(className, methodName)`
3. 在 Application 的 onCreate() 里加上：
```java
LogcatUtil.getInstance().setOutOfMemoryListener(new OutOfMemoryListener() {
            @Override
            public void onOutOfMemory() {
                // 在这里你可以把记录的内存信息发给你们记录日志的服务器
                Log.d(TAG, "onOutOfMemory : " + MemoryLog.getLog());
            }
        }).start();
```
4. 调用`MemoryLog.setPrintLog(true)`可以打印log
### 效果

在每次调用 `MemoryLog.addLog(this)` 的时候，MemoryLog 都会记录一条当前的内存信息，格式如下：

```
MainActivity [onCreate] (1.68M + 16.15M)/256.00M
```

其中，1.68M表示 app 当前空闲的内存大小，16.15M表示 app 目前已使用的内存大小，256.00M 表示 app 最大可用的内存大小。

当 app 发生 OutOfMemory 问题时，OutOfMemoryListener 会收到回调，你可以在 `onOutOfMemory()` 里把之前记录好的内存信息发给你们的服务器。内存信息可以通过 `MemoryLog.getLog()`获取到，格式如下：

```
MainActivity [onCreate] (1.68M + 16.15M)/256.00M
SecondActivity [onCreate] (0.15M + 16.15M)/256.00M
ThirdActivity [onCreate] (4.60M + 16.15M)/256.00M
ThirdActivity [onDestroy] (4.48M + 16.15M)/256.00M
SecondActivity [onDestroy] (4.48M + 16.15M)/256.00M
MainActivity [onDestroy] (4.47M + 16.15M)/256.00M
```

通过分析这些数据，可以帮你粗略地定位到哪里的内存开销较大，之后再针对那一块去做优化就行了。
