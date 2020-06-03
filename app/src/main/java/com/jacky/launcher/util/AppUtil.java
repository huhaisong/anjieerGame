package com.jacky.launcher.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppUtil {
    private static final String TAG = "AppUtil";

    public static String getAPPVersion(Context context) {
        Log.e(TAG, "getAPPVersion: time = " + DateUtil.LongToString(Long.valueOf(android.os.Build.TIME)));

        PackageManager pm = context.getPackageManager();//得到PackageManager对象
        String appVersion = "0.0";
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);//得到PackageInfo对象，封装了一些软件包的信息在里面
            appVersion = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //Log.e(TAG, "getAppVersion:"+e.getCause());
        }

  /*      String userId = android.os.Build.DISPLAY;
        // id= xx user.xxx.20200321.141223 test-keys
        Log.e(TAG, "getAPPVersion: "+userId );
        String estVersion = userId.substring(userId.indexOf(" user."));
        estVersion = estVersion.substring(6, estVersion.length());
        int first = estVersion.indexOf(".");
        estVersion = estVersion.substring((first + 1), (first + 1 + 8)) + " V" + appVersion;*/
        return (DateUtil.LongToString(Long.valueOf(android.os.Build.TIME)) + " V" + appVersion);
    }

}
