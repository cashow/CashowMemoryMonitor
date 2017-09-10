package com.cashow.cashowmemorymonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cashow.library.MemoryLog;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MemoryLog.setPrintLog(true);
        // record memory info
        MemoryLog.addLog(this);
        test();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // record memory info
        MemoryLog.addLog(this);
    }

    private void test() {
        MemoryLog.addLog("BaseActivity", "test");
    }
}
