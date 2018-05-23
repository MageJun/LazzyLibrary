package com.zed3.sipua.xydj.ui.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao<T>{

    protected Dao mDao;
    protected Context mContext;
    public BaseDao(Context context){
        mContext = context;
        loadDao();
    }
    protected abstract  void loadDao();

    /**
     * 插入数据
     * @param t
     */
    public T inert(T t){
        loadDao();
        try {
            return (T) mDao.createIfNotExists(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int delete(T t){
        try {
            return mDao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int update(T t){
        try {
           return  mDao.update(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<T> query(PreparedQuery<T> preparedQuery){
        try {
            return mDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T>  query(){
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
