package com.boxuegu.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/10/30 0030.
 */

public class SQLiteHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION=1;
    public static String DB_NAME="bxg,db";
    public static final String U_USERINFO="userinfo";

    public SQLiteHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "
                    +U_USERINFO
                    +"("
                    +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"userName VARCHAR, "
                    +"nickName VARCHAR, "
                    +"sex VARCHAR, "
                    +"signature VARCHAR"
                    +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+U_USERINFO);
        onCreate(db);
    }
}
