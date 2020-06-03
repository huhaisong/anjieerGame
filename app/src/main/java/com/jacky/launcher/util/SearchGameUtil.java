package com.jacky.launcher.util;

import android.content.Context;

import com.jacky.launcher.BaseApplication;
import com.jacky.launcher.config.Game;
import com.jacky.launcher.daomanager.GameDaoManager;
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

    private static void getTfcardFileList(Game game) {
        int j;
        String Name;
        String[] listFiles = null;
        File file = new File("/mnt/external_sd/" + game.getName() + "/roms/");
        if (file.exists()) {
            listFiles = file.list();
        } else {
            return;
        }
        if (listFiles == null || listFiles.length == 0)
            return;
        Arrays.sort(listFiles);
        if (game == Game.VARC) {
            for (int i = 0; i < listFiles.length; i++) {
                j = listFiles[i].indexOf(".zip");
                if (j > 0) {
                    Name = listFiles[i].substring(0, j);
                    if (!ARCBios.contains(Name)) {
                        GameDaoManager.getInstance().insertOrReplaceByName(Name);
                    }
                }
            }
        } else {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].endsWith(game.getSuffix())) {
                    j = listFiles[i].indexOf(game.getSuffix());
                    if (j > 0) {
                        Name = listFiles[i].substring(0, j);
                        GameDaoManager.getInstance().insertOrReplaceByName(Name);
                    }
                }
            }
        }
    }

    public static void searchROMList(Context context) { // at bootup or insert tfcard
        DBUtil.copyDbFile(context);
        for (Game game : Game.values()) {
            getTfcardFileList(game);
        }
    }
}
