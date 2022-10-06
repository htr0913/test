package com.htr.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.htr.test.utils.JNIUtil;
import com.htr.test.workmanager.WorkActivity;

public class MainActivity extends AppCompatActivity {
    long THIRTY_SECOND = 30 * 1000;
    long ONE_MINUTE = 60 * 1000;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnClickListener clickListener = new OnClickListener();

        Button btnSchedulerJob = findViewById(R.id.btnScheduler);
        btnSchedulerJob.setOnClickListener(clickListener);

        Button btnCancelJob = findViewById(R.id.btnCancel);
        btnCancelJob.setOnClickListener(clickListener);

        Button btnWorkActivity = findViewById(R.id.btnWorkActivity);
        btnWorkActivity.setOnClickListener(clickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        printBaseInfo();

    }

    private class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnWorkActivity:
                    startActivity(new Intent(MainActivity.this, WorkActivity.class));
                    break;
                    // TODO move job to WorkManager pkg
                case R.id.btnScheduler:
                    scheduleJob();
                    break;
                case R.id.btnCancel:
                    cancelJob();
                    break;
                default:
                    break;
            }
        }
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


}