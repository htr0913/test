package com.htr.test;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "job start");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "job stop");
        return false;
    }




}