package com.cashow.cashowmemorymonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cashow.library.MemoryLog;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // record memory info
        MemoryLog.addLog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // record memory info
        MemoryLog.addLog(this);
    }
}
