package com.htr.test;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();

    private JobParameters mJobParameters;
    public MyJobService() {
    }



    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "job start");
        mJobParameters = jobParameters;
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "job stop");
        return false;
    }


}