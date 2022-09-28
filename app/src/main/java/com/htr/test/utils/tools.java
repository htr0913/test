package com.htr.test.utils;

import android.os.Process;

public class tools {

    public static String baseProcessInfo() {
        StringBuilder builder = new StringBuilder();
        int pid = Process.myPid();
        int tid = Process.myTid();
        int uid = Process.myUid();
        return builder
                .append("UID= ").append(uid)
                .append("PID= ").append(pid)
                .append("TID= ").append(tid).toString();
    }
}
