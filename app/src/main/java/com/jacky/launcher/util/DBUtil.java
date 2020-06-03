package com.jacky.launcher.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBUtil {
    //数据库文件路径
    private static final String DB_PATH = "/data/data/包名/databases/";
    //数据库文件名
    private static final String DB_NAME = "aje.db";
    private static final String DATABASE_NAME = "aje.db";  //数据库名字

    public static void copyDbFile(Context context) {
        InputStream in = null;
        FileOutputStream out = null;
        String path = "/data/data/" + context.getPackageName() + "/databases/";
        File file = new File(path + DB_NAME);

        //创建文件夹
        File filePath = new File(path);
        if (!filePath.exists())
            filePath.mkdirs();
        try {
            in = context.getAssets().open(DB_NAME); // 从assets目录下复制
            out = new FileOutputStream(file);
            int length = -1;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
