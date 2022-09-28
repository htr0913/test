package com.htr.test.workmanager;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {
    private static final String TAG = MyWork.class.getSimpleName();
    public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int pid = Process.myPid();
        int tid = Process.myTid();
        int uid = Process.myUid();
        Log.i(TAG, "doWork is under pid:" + pid + ", tid:" + tid);
        return Result.success();
    }



}
