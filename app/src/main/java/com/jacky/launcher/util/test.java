package com.jacky.launcher.util;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.jacky.launcher.BaseApplication;

import java.io.File;

public class test {

    public void test() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(BaseApplication.getINSTANCE(), uri);
        rt.play();
    }
}
