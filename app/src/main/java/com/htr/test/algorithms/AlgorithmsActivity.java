package com.htr.test.algorithms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.htr.test.R;
import com.htr.test.utils.JNIUtil;
import java.util.Random;

public class AlgorithmsActivity extends AppCompatActivity {
    private static final String TAG = AlgorithmsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithms);
        findViewById(R.id.btnArray).setOnClickListener(view -> arrayOperation());
    }

    private void arrayOperation() {
        Random random = new Random();
        int length = random.nextInt(10);
        long nativeData = JNIUtil.initArray(length);
        Log.i(TAG, "Array pointer:" + nativeData);
        JNIUtil.showArray(nativeData);
        for (int i=0; i<length; i++) {
            JNIUtil.appendArray(nativeData, random.nextInt(100));
        }
        JNIUtil.showArray(nativeData);
        JNIUtil.reverseArray(nativeData);
        JNIUtil.showArray(nativeData);
        JNIUtil.deleteArray(nativeData, length / 2);
        JNIUtil.showArray(nativeData);
        JNIUtil.insertArray(nativeData, length /2, random.nextInt(1000));
        JNIUtil.showArray(nativeData);
    }
}