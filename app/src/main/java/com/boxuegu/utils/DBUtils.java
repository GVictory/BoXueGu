package com.boxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.boxuegu.bean.UserBean;
import com.boxuegu.sqlite.SQLiteHelper;

/**
 * Created by Administrator on 2018/11/6 0006.
 */

public class DBUtils {
    private static DBUtils instance=null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;
    public DBUtils(Context context){
        helper=new SQLiteHelper(context);
        db=helper.getWritableDatabase();
    }

    public static DBUtils getInstance(Context context) {
        if (instance==null){
            return new DBUtils(context);
        }
        return instance;
    }

    public void saveUserInfo(UserBean userBean){
        ContentValues values=new ContentValues();
        values.put("userName",userBean.userName);
        values.put("nickName",userBean.nickName);
        values.put("sex",userBean.sex);
        values.put("signature",userBean.signature);
        db.insert(SQLiteHelper.U_USERINFO,null,values);
    }

    public UserBean getUserInfo(String userName){
        String sql="SELECT * FROM "+SQLiteHelper.U_USERINFO+
                    " WHERE userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        UserBean bean=null;
        while (cursor.moveToNext()){
            bean=new UserBean();
            bean.userName=cursor.getString(cursor.getColumnIndex("userName"));
            bean.nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            bean.sex=cursor.getString(cursor.getColumnIndex("sex"));
            bean.signature=cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return bean;
    }

    public void updateUserInfo(String key,String value,String userName){
        ContentValues contentValues=new ContentValues();
        contentValues.put(key,value);
        db.update(SQLiteHelper.U_USERINFO,contentValues,"userName=?",new String[]{userName});
    }
}
