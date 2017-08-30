package com.cashow.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogcatUtil {

    private static LogcatUtil INSTANCE = null;
    private LogDumper mLogDumper = null;
    private int mPId;

    private OutOfMemoryListener outOfMemoryListener;

    public static LogcatUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogcatUtil();
        }
        return INSTANCE;
    }

    public LogcatUtil setOutOfMemoryListener(OutOfMemoryListener outOfMemoryListener) {
        this.outOfMemoryListener = outOfMemoryListener;
        return this;
    }

    private LogcatUtil() {
        mPId = android.os.Process.myPid();
    }

    public void start() {
        if (mLogDumper == null)
            mLogDumper = new LogDumper(String.valueOf(mPId));
        if (!mLogDumper.isAlive()) {
            try {
                MemoryLog.init();
                mLogDumper.start();
            } catch (Exception e) {
            }
        }
    }

    public void stop() {
        if (mLogDumper != null) {
            try {
                mLogDumper.stopLogs();
                mLogDumper = null;
            } catch (Exception e) {
            }
        }
    }

    private class LogDumper extends Thread {
        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;

        public LogDumper(String pid) {
            mPID = pid;
            cmds = "logcat *:e | grep \"(" + mPID + ")\" | grep OutOfMemory";
        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()));
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (line.contains(mPID) && line.contains("OutOfMemory")) {
                        if (outOfMemoryListener != null) {
                            outOfMemoryListener.onOutOfMemory();
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
