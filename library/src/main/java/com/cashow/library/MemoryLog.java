package com.cashow.library;

import android.util.Log;

import java.util.Locale;

public class MemoryLog {

    private static StringBuffer stringBuffer;

    private static final String TAG = "MemoryMonitor";

    private static boolean isPrintLog;

    public static void init() {
        stringBuffer = new StringBuffer();
    }

    public static void addLog(Object object) {
        String methodName = getMethodNames(new Throwable().getStackTrace());
        addLog(object.getClass().getSimpleName(), methodName);
    }

    public static void addLog(String className, String methodName) {
        String memoryLog = getMemoryLog(className, methodName);
        stringBuffer.append("\n");
        stringBuffer.append(memoryLog);
        if (isPrintLog) {
            Log.d(TAG, "addLog : " + memoryLog);
        }
    }

    public static void setPrintLog(boolean printLog) {
        isPrintLog = printLog;
    }

    private static String getMethodNames(StackTraceElement[] sElements){
        return sElements[1].getMethodName();
    }

    private static String getMemoryInfo() {
        String memoryInfo = "";
        try {
            Runtime info = Runtime.getRuntime();
            long freeSize = info.freeMemory();
            long totalSize = info.totalMemory();
            long maxSize = info.maxMemory();
            double freeMemory = freeSize / 1048576.0;
            double totalMemory = totalSize / 1048576.0;
            double maxMemory = maxSize / 1048576.0;
            memoryInfo = String.format(Locale.getDefault(), "(%.2fM + %.2fM)/%.2fM", freeMemory, totalMemory, maxMemory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memoryInfo;
    }

    private static String getMemoryLog(String className, String methodName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(className);
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
