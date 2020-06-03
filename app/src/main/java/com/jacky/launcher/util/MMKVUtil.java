package com.jacky.launcher.util;

import com.jacky.launcher.config.Config;
import com.tencent.mmkv.MMKV;

public class MMKVUtil {
    private static MMKV mmkv = MMKV.defaultMMKV();

    public static boolean getVoiceState() {
        return mmkv.getBoolean(Config.VOICE_SETTING, false);
    }

    public static void setVoiceSetting(boolean voiceSetting) {
        mmkv.encode(Config.VOICE_SETTING, voiceSetting);
    }

    public static int getLightState() {
        return mmkv.getInt(Config.LIGHT_SETTING, 180);
    }

    public static void setLightSetting(int lightSetting) {
        mmkv.encode(Config.LIGHT_SETTING, lightSetting);
    }

    public static int getLanguage() {
        return mmkv.getInt(Config.LANGUAGE, 180);
    }

    public static void setLanguage(int language) {
        mmkv.encode(Config.LANGUAGE, language);
    }

    public static int getLockState() {
        return mmkv.getInt(Config.LOCK_SETTING, 0);
    }

    public static void setLockSetting(int lightSetting) {
        mmkv.encode(Config.LOCK_SETTING, lightSetting);
    }

}