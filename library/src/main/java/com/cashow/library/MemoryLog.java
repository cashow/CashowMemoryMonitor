package com.cashow.library;

import java.util.Locale;

public class MemoryLog {

    private static StringBuffer stringBuffer;

//    private static final String TAG = "MemoryMonitor";

    public static void init() {
        stringBuffer = new StringBuffer();
    }

    public static void addLog(Object object) {
        String methodName = getMethodNames(new Throwable().getStackTrace());
        String memoryLog = getMemoryLog(object, methodName);
        stringBuffer.append("\n");
        stringBuffer.append(memoryLog);
//        Log.d(TAG, "addLog : " + memoryLog);
    }

    private static String getMethodNames(StackTraceElement[] sElements){
        return sElements[1].getMethodName();
    }

    private static String getMemoryInfo() {
        String memoryInfo = "";
        try {
            Runtime info = Runtime.getRuntime();
            long freeSize = info.freeMemory();
            long maxSize = info.maxMemory();
            double freeMemory = freeSize / 1048576.0;
            double maxMemory = maxSize / 1048576.0;
            memoryInfo = String.format(Locale.getDefault(), "%.2fM/%.2fM", freeMemory, maxMemory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memoryInfo;
    }

    private static String getMemoryLog(Object object, String methodName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(object.getClass().getSimpleName());
        buffer.append(" [");
        buffer.append(methodName);
        buffer.append("] ");
        buffer.append(getMemoryInfo());
        return buffer.toString();
    }

    public static String getLog() {
        return stringBuffer.toString();
    }
}
