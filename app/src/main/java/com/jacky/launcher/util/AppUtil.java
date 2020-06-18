package com.jacky.launcher.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

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


    public static void openActivity(Context context, String appName) {
        // 获取包管理器
        PackageManager manager = context.getPackageManager();
        // 指定入口,启动类型,包名
        Intent intent = new Intent(Intent.ACTION_MAIN);//入口Main
        intent.addCategory(Intent.CATEGORY_LAUNCHER);// 启动LAUNCHER,跟MainActivity里面的配置类似
        intent.setPackage(appName);//包名
        //查询要启动的Activity
        List<ResolveInfo> apps = manager.queryIntentActivities(intent, 0);
        if (apps.size() > 0) {//如果包名存在
            ResolveInfo ri = apps.get(0);
            // //获取包名
            String packageName = ri.activityInfo.packageName;
            //获取app启动类型
            String className = ri.activityInfo.name;
            //组装包名和类名
            ComponentName cn = new ComponentName(packageName, className);
            //设置给Intent
            intent.setComponent(cn);
            //根据包名类型打开Activity
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "找不到包名;" + appName, Toast.LENGTH_SHORT).show();
        }
    }

}
