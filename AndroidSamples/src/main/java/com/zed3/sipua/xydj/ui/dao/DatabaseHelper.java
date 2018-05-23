package com.zed3.sipua.xydj.ui.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zed3.sipua.xydj.ui.dao.domain.GroupInvite;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "mobile_xydj";
    private static final int VERSION = 1;
    private static DatabaseHelper sInstance;
    private Map<Class,Dao> mDaos = new HashMap<>();

    private DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    public static DatabaseHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new DatabaseHelper(context,DATABASE_NAME,null,VERSION);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, GroupInvite.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,GroupInvite.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao getDao(Class cls){
        Dao dao = mDaos.get(cls);
        if(dao==null){
            try {
                dao =super.getDao(cls);
                mDaos.put(cls,dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for(Class cls:mDaos.keySet()){
            Dao dao = mDaos.get(cls);
            dao=null;
            mDaos.remove(cls);
        }
    }
}
