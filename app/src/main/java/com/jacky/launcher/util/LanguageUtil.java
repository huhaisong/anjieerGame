package com.jacky.launcher.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageUtil {

    public static void switchLanguage(Context mActivity, String string) {
        Resources resources = mActivity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        if (!string.equals("chinese")) {
            config.locale = Locale.CHINESE;
        } else {
            config.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(config, dm);
        MMKVUtil.setLanguage(string);
    }

}
