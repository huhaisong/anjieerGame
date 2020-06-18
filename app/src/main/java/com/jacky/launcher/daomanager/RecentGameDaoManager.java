package com.jacky.launcher.daomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jacky.launcher.dao.DaoMaster;
import com.jacky.launcher.dao.DaoSession;
import com.jacky.launcher.dao.GameEntityDao;
import com.jacky.launcher.dao.RecentGameEntityDao;
import com.jacky.launcher.entity.GameEntity;
import com.jacky.launcher.entity.RecentGameEntity;

import java.util.List;

public class RecentGameDaoManager {

    private static RecentGameDaoManager INSTANCE;
    private RecentGameEntityDao gameEntityDao;

    public static synchronized RecentGameDaoManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RecentGameDaoManager(context);
        }
        return INSTANCE;
    }

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context mContext;

    public RecentGameDaoManager(Context context) {
        this.mContext = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "aje1.db");
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        gameEntityDao = mDaoSession.getRecentGameEntityDao();
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplaceByName(RecentGameEntity gameEntity) {
        gameEntityDao.insertOrReplace(gameEntity);
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplaceAll(List<RecentGameEntity> friendEntities) {
        gameEntityDao.deleteAll();
        gameEntityDao.insertOrReplaceInTx(friendEntities);
    }

    /**
     * 查询所有数据
     */
    public List<RecentGameEntity> searchAll() {
        List<RecentGameEntity> searchHistories = gameEntityDao.loadAll();
        List<GameEntity> allGames = GameDaoManager.getInstance(mContext).searchAll();
        for (RecentGameEntity item : searchHistories) {
            for (GameEntity game : allGames) {
                if (!item.getName().equals(game.getName())) {
                    searchHistories.remove(item);
                    gameEntityDao.delete(item);
                }
            }
        }
        return searchHistories;
    }

  /*  public FriendEntity searchById(int id){
      return friendEntityDao.queryBuilder().where(FriendEntityDao.Properties.Id.eq(id)).build().unique();
    }*/

    public void deleteAll() {
        gameEntityDao.deleteAll();
    }

}
