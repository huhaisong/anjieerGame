package com.jacky.launcher.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kongzue.dialog.v3.WaitDialog;

public class DialogBroadcastReceiver extends BroadcastReceiver {

    public static String SHOW_DIALOG = "show_dialog";
    public static String DISMISS_DIALOG = "dismiss_dialog";

    private static final String TAG = "DialogBroadcastReceiver";

    // 复写onReceive()方法
    // 接收到广播后，则自动调用该方法
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " + intent.getAction());
        if (intent.getAction().equals(SHOW_DIALOG))
            //写入接收广播后的操作
            WaitDialog.show((AppCompatActivity) context, "正在扫描请耐心等待！");
        else if (intent.getAction().equals(DISMISS_DIALOG)) {
            WaitDialog.dismiss();
        }
    }
}
