package com.htr.test.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.htr.test.R;

public class WorkActivity extends AppCompatActivity {
    private static final String TAG = WorkActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Button btnEnqueueWork = findViewById(R.id.btnEnqueueWork);
        btnEnqueueWork.setOnClickListener(view -> enqueueWork(this));
    }

    private void enqueueWork(Context context) {
        // Create charging constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(FirstWork.class)
                .setConstraints(constraints)
                .addTag(TAG)
                .build();

//        Android Studio highlights MyJobService
//        Configuration configuration = new Configuration.Builder()
//                .setJobSchedulerJobIdRange(0, 100)
//                .build();
//        WorkManager.initialize(context, configuration);

        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueue(request);

    }
}