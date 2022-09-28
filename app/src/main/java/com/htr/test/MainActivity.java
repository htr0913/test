package com.htr.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.htr.test.workmanager.MyWork;

public class MainActivity extends AppCompatActivity {
    long THIRTY_SECOND = 30 * 1000;
    long ONE_MINUTE = 60 * 1000;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSchedulerJob = findViewById(R.id.btnScheduler);
        btnSchedulerJob.setOnClickListener(view -> scheduleJob());

        Button btnCancelJob = findViewById(R.id.btnCancel);
        btnCancelJob.setOnClickListener(view -> cancelJob());
    }

    @Override
    protected void onStart() {
        super.onStart();

        printBaseInfo();

    }

    private void printBaseInfo() {
        long versionCode = 0;
        try {
            PackageInfo packageInfo = getPackageManager().
                    getPackageInfo(getPackageName(), 0);
            //build.gradle
            versionCode = packageInfo.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

        int tid = Process.myTid();
        Log.i(TAG,"version: " + versionCode + ", main thread: " + tid);
    }

    private void scheduleJob() {
        int jobId = 1024;
        JobInfo.Builder builder =
                new JobInfo.Builder(jobId, new ComponentName(this, MyJobService.class));

        builder.setMinimumLatency(THIRTY_SECOND)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .setOverrideDeadline(ONE_MINUTE);

        JobInfo jobinfo = builder.build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        Log.i(TAG, "scheduler: " + jobinfo.toString());
        scheduler.schedule(jobinfo);
    }

    private void cancelJob() {
        int jobId = 1024;
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        Log.i(TAG, "cancel: " + jobId);
        scheduler.cancel(jobId);
    }

    private void enqueueWork(Context context) {
        // Create charging constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWork.class)
                .setConstraints(constraints)
                .addTag(TAG)
                .build();

        Configuration configuration = new Configuration.Builder()
                .setJobSchedulerJobIdRange(0, 100).build();

        WorkManager.initialize(context, configuration);
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueue(request);

    }
}