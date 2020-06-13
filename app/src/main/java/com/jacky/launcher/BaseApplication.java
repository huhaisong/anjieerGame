package com.jacky.launcher;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.jacky.launcher.dao.DaoMaster;
import com.jacky.launcher.dao.DaoSession;
import com.jacky.launcher.util.DBUtil;
import com.jacky.launcher.util.SearchGameUtil;
import com.kongzue.dialog.v3.WaitDialog;
import com.tencent.mmkv.MMKV;

import java.io.IOException;

public class BaseApplication extends Application {

    private static BaseApplication INSTANCE;


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        MMKV.initialize(this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                initGreenDao();
                SearchGameUtil.searchROMList(BaseApplication.this);
            }
        }).start();
    }

    public static BaseApplication getINSTANCE() {
        return INSTANCE;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    /**
     * 初始化dao
     */
    private void initGreenDao() {
        mHelper = new DaoMaster.DevOpenHelper(INSTANCE, "aje.db");
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
