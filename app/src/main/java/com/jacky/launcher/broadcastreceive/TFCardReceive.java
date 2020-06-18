package com.jacky.launcher.broadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jacky.launcher.daomanager.RecentGameDaoManager;
import com.jacky.launcher.util.SearchGameUtil;

public class TFCardReceive extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RecentGameDaoManager.getInstance(context).deleteAll();
                SearchGameUtil.searchROMList(context);
            }
        }).start();
    }
}
