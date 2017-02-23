package com.example.kds.doubleprocessdaemon;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * Created by houpeng on 2017/2/23.
 */

public class SecondService extends Service {

    private MyBind mBind;
    private MyConnection mConnection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(mBind == null){
            mBind = new MyBind();
        }
        if(mConnection == null){
            mConnection = new MyConnection();
        }
        return mBind;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mConnection == null){
            mConnection = new MyConnection();
        }
        this.bindService(new Intent(SecondService.this, firstService.class),
                mConnection, Context.BIND_IMPORTANT);
        Notification.Builder builder = new Notification.Builder(this)
                //设置图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置标题
                .setContentTitle("请保持程序后台运行")
                //设置内容
                .setContentText("请保持程序后台运行");

        startForeground(startId, builder.build());
        return START_STICKY;
    }

    class MyBind extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }



    class MyConnection implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务挂了重新启动
            startService(new Intent(SecondService.this, firstService.class));
            //链接本地服务
            bindService(new Intent(SecondService.this, firstService.class)
                    , mConnection, Context.BIND_IMPORTANT);
        }
    }
}
