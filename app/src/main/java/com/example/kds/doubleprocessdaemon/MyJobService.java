package com.example.kds.doubleprocessdaemon;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

/**
 * Created by houpeng on 2017/2/23.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService  extends JobService{

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            JobParameters obj = (JobParameters) msg.obj;
            jobFinished(obj, true);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Message m = Message.obtain();
        m.obj = params;
        mHandler.sendMessage(m);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mHandler.removeCallbacksAndMessages(null);
        return false;
    }
}
