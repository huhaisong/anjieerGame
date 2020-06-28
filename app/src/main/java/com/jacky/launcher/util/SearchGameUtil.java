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

    private static void getTfcardFileList(final Game game, final Context context) {
        long nowTime = System.currentTimeMillis();
        int j;
        String Name;
        Log.e(TAG, "searchROMList: 开始扫描" + game.getName() + "： System.currentTimeMillis() = " + System.currentTimeMillis());
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
        Log.e(TAG, "searchROMList: 开始排序" + game.getName() + "： System.currentTimeMillis() = " + System.currentTimeMillis());
        Arrays.sort(listFiles);
        Log.e(TAG, "searchROMList: 结束排序，开始判断条件并插入数据库" + game.getName() + "： System.currentTimeMillis() = " + System.currentTimeMillis());
        if (game == Game.VARC) {
            for (int i = 0; i < listFiles.length; i++) {
                Log.e(TAG, "getTfcardFileList: " + listFiles[i]);
                j = listFiles[i].indexOf(".zip");
                if (j > 0) {
                    Name = listFiles[i].substring(0, j);
                    if (!ARCBios.contains(Name)) {
                        final String finalName = Name;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GameDaoManager.getInstance(context).insertOrReplaceByName(finalName, game.getName());
                            }
                        }).start();
                    }
                }
            }
        } else {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].endsWith(game.getSuffix())) {
                    j = listFiles[i].indexOf(game.getSuffix());
                    Log.e(TAG, "getTfcardFileList: " + listFiles[i]);
                    if (j > 0) {
                        Name = listFiles[i].substring(0, j);
                        final String finalName1 = Name;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GameDaoManager.getInstance(context).insertOrReplaceByName(finalName1, game.getName());
                            }
                        }).start();
                    }
                }
            }
        }
        Log.e(TAG, "getTfcardFileList: 扫描" + game.getName() + "花费的时间：" + (System.currentTimeMillis() - nowTime) + ",System.currentTimeMillis() = " + System.currentTimeMillis());
    }

    public static Boolean isSearching = false;


    public static void searchROMList(Context context) { // at bootup or insert tfcard
        if (isSearching)
            return;
        isSearching = true;
        Intent intent = new Intent(DialogBroadcastReceiver.SHOW_DIALOG);
        context.sendBroadcast(intent);
        long now = System.currentTimeMillis();
        Log.e(TAG, "searchROMList: 开始复制db： System.currentTimeMillis() = " + System.currentTimeMillis());
        DBUtil.copyDbFile(context);
        Log.e(TAG, "searchROMList: 复制db时间：" + (System.currentTimeMillis() - now) + ",System.currentTimeMillis() = " + System.currentTimeMillis());
        now = System.currentTimeMillis();
        for (Game game : Game.values()) {
            getTfcardFileList(game, context);
        }
        Log.e(TAG, "searchROMList: 搜索的总时间：" + (System.currentTimeMillis() - now) + ",System.currentTimeMillis() = " + System.currentTimeMillis());
        isSearching = false;
        Intent intent1 = new Intent(DialogBroadcastReceiver.DISMISS_DIALOG);
        context.sendBroadcast(intent1);
    }

    public interface FinishSearchListen {
        void onFinished();
    }
}
