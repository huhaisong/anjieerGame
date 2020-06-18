package com.jacky.launcher.daomanager;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jacky.launcher.BaseApplication;
import com.jacky.launcher.config.Game;
import com.jacky.launcher.dao.DaoMaster;
import com.jacky.launcher.dao.DaoSession;
import com.jacky.launcher.dao.GameEntityDao;
import com.jacky.launcher.entity.GameEntity;

import java.util.ArrayList;
import java.util.List;

public class GameDaoManager {

    private static GameDaoManager INSTANCE;
    private GameEntityDao gameEntityDao;

    public static synchronized GameDaoManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GameDaoManager(context);
        }
        return INSTANCE;
    }

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public GameDaoManager(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context, "aje.db");
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        gameEntityDao = mDaoSession.getGameEntityDao();
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplaceByName(String name) {
        try {
            GameEntity mOldResponseBean = gameEntityDao.queryBuilder().where(GameEntityDao.Properties.Name.eq(name)).build().unique();//拿到之前的记录
            if (mOldResponseBean != null) {
                mOldResponseBean.setVisiable(0);
                gameEntityDao.insertOrReplace(mOldResponseBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplaceAll(List<GameEntity> friendEntities) {
        gameEntityDao.deleteAll();
        gameEntityDao.insertOrReplaceInTx(friendEntities);
    }

    /**
     * 查询所有数据
     */
    public List<GameEntity> searchAll() {
        List<GameEntity> searchHistories = gameEntityDao.queryBuilder().where(GameEntityDao.Properties.Visiable.eq(0)).list();
        return searchHistories;
    }

  /*  public FriendEntity searchById(int id){
      return friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(id)).build().unique();
    }*/

    public void deleteAll() {
        gameEntityDao.deleteAll();
    }


    /**
     * 根据输入内容likeassetName  模糊查询 复合条件的name 和code两个字段
     *
     * @param
     */
    public List<GameEntity> queryByType(String type) {
        return gameEntityDao.queryBuilder().where(GameEntityDao.Properties.Type.eq(type), GameEntityDao.Properties.Visiable.eq(0)).list();
    }
}
