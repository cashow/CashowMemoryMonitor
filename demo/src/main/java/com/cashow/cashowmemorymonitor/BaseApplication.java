package com.cashow.cashowmemorymonitor;

import android.app.Application;
import android.util.Log;

import com.cashow.library.LogcatUtil;
import com.cashow.library.MemoryLog;
import com.cashow.library.OutOfMemoryListener;

public class BaseApplication extends Application {
    private static final String TAG = "MemoryMonitor";

    @Override
    public void onCreate() {
        super.onCreate();

        LogcatUtil.getInstance().setOutOfMemoryListener(new OutOfMemoryListener() {
            @Override
            public void onOutOfMemory() {
                // you can send memory log to your server in here
                Log.d(TAG, "onOutOfMemory : " + MemoryLog.getLog());
            }
        }).start();
    }
}
