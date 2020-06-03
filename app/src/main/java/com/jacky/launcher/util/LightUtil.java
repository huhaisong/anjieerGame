package com.jacky.launcher.util;

import android.app.Activity;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.jacky.launcher.BaseApplication;
import com.jacky.launcher.R;

public class LightUtil {

    //系统亮度为0-255
    public static void setBrightness(int current, Activity activity) {
//        Settings.System.putInt(BaseApplication.getINSTANCE().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, current);
        //设置当前activity的屏幕亮度
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        //0到1,调整亮度暗到全亮
        lp.screenBrightness = (float) current * 255.0f / 255.0f / 255.0f;
        activity.getWindow().setAttributes(lp);
    }

    public int getSystemBrightness() {
        //获取当前亮度,获取失败则返回255
        int brightness = Settings.System.getInt(BaseApplication.getINSTANCE().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
        return brightness;
    }
}
