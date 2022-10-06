package com.htr.test.workmanager;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class FirstWork extends Worker {
    private static final String TAG = FirstWork.class.getSimpleName();

    private static final long FIVE_SECOND = 5 * 1000L;

    public FirstWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int pid = Process.myPid();
        int tid = Process.myTid();
        int uid = Process.myUid();
        Log.i(TAG, "doWork is under uid:" + uid + ", pid:" + pid + ", tid:" + tid);
        long now = SystemClock.elapsedRealtime();
        Log.i(TAG, "sleep in first work, now:" + now);
        SystemClock.sleep(FIVE_SECOND);
        Log.i(TAG,"elapsed:" + (SystemClock.elapsedRealtime() - now));
        return Result.success();
    }



}
