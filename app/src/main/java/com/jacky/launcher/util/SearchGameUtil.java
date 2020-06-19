package com.jacky.launcher.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jacky.launcher.BaseApplication;
import com.jacky.launcher.broadcast.DialogBroadcastReceiver;
import com.jacky.launcher.config.Game;
import com.jacky.launcher.daomanager.GameDaoManager;
import com.jacky.launcher.daomanager.RecentGameDaoManager;
import com.jacky.launcher.entity.GameEntity;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class SearchGameUtil {
    private static final String TAG = "searchRomFile";

    static HashSet<String> ARCBios = new HashSet<String>() {
        {
            add("acpsx");
            add("aleck64");
            add("alg_bios");
            add("ar_bios");
            add("atarisy1");
            add("atluspsx");
            add("atpsx");
            add("awbios");
            add("bctvidbs");
            add("cd32");
            add("cpzn1");
            add("cpzn2");
            add("crysbios");
            add("decocass");
            add("galgbios");
            add("hikaru");
            add("hng64");
            add("hod2bios");
            add("konamigv");
            add("konamigx");
            add("kviper");
            add("macsbios");
            add("maxaflex");
            add("megaplay");
            add("megatech");
            add("naomi");
            add("naomi2");
            add("naomigd");
            add("neogeo");
            add("nss");
            add("pgm");
            add("playch10");
            add("psarc95");
            add("skns");
            add("stvbios");
            add("sys573");
            add("taitofx1");
            add("taitogn");
            add("tps");
            add("vspsx");
        }
    };


    private static void getTfcardFileList(Game game, Context context) {
        int j;
        String Name;
        String[] listFiles = null;
        File file = new File("/mnt/external_sd/" + game.getName() + "/roms/");
        if (file.exists()) {
            listFiles = file.list();
        } else {
            Log.e(TAG, "getTfcardFileList: " + "没有找到" + game.getName() + "文件夹！");
            return;
        }
        if (listFiles == null || listFiles.length == 0) {
            if (listFiles == null) {
                Log.e(TAG, "getTfcardFileList: " + game.getName() + "listFiles == null" + ",path = " + file.getAbsolutePath());
                return;
            }
            Log.e(TAG, "getTfcardFileList: " + game.getName() + "listFiles.length == 0" + ",path = " + file.getAbsolutePath());
            return;
        }
        Arrays.sort(listFiles);
        if (game == Game.VARC) {
            for (int i = 0; i < listFiles.length; i++) {
                Log.e(TAG, "getTfcardFileList: " + listFiles[i]);
                j = listFiles[i].indexOf(".zip");
                if (j > 0) {
                    Name = listFiles[i].substring(0, j);
                    if (!ARCBios.contains(Name)) {
                        GameDaoManager.getInstance(context).insertOrReplaceByName(Name);
                    }
                }
            }
        } else {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].endsWith(game.getSuffix())) {
                    Log.e(TAG, "getTfcardFileList: " + listFiles[i]);
                    j = listFiles[i].indexOf(game.getSuffix());
                    if (j > 0) {
                        Name = listFiles[i].substring(0, j);
                        GameDaoManager.getInstance(context).insertOrReplaceByName(Name);
                    }
                }
            }
        }
    }

    public static Boolean isSearching = false;

    public static void searchROMList(Context context) { // at bootup or insert tfcard
        isSearching = true;
        Intent intent = new Intent(DialogBroadcastReceiver.SHOW_DIALOG);
        context.sendBroadcast(intent);
        DBUtil.copyDbFile(context);
        for (Game game : Game.values()) {
            getTfcardFileList(game, context);
        }
        isSearching = false;
        Intent intent1 = new Intent(DialogBroadcastReceiver.DISMISS_DIALOG);
        context.sendBroadcast(intent1);
    }

    public interface FinishSearchListen {
        void onFinished();
    }
}
