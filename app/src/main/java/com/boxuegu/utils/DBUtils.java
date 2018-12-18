package com.boxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.boxuegu.bean.UserBean;
import com.boxuegu.bean.VideoBean;
import com.boxuegu.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

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

    public void saveVideoPlayList(VideoBean bean, String userName) {
        // 判断如果里面有此播放记录则需删除重新存放
        if (hasVideoPlay(bean.chapterId, bean.videoId,userName)) {
            // 删除之前存入的播放记录
            boolean isDelete=delVideoPlay(bean.chapterId, bean.videoId,userName);
            if(!isDelete){
                //没有删除成功时，则需跳出此方法不再执行下面的语句
                return;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put("userName", userName);
        cv.put("chapterId", bean.chapterId);
        cv.put("videoId", bean.videoId);
        cv.put("videoPath", bean.videoPath);
        cv.put("title", bean.title);
        cv.put("secondTitle", bean.secondTitle);
        db.insert(SQLiteHelper.U_VIDEO_PLAY_LIST, null, cv);
    }
    /**
     * 判断视频记录是否存在
     */
    public boolean hasVideoPlay(int chapterId, int videoId,String userName) {
        boolean hasVideo = false;
        String sql = "SELECT * FROM " + SQLiteHelper.U_VIDEO_PLAY_LIST
                + " WHERE chapterId=? AND videoId=? AND userName=?";
        Cursor cursor = db.rawQuery(sql, new String[] { chapterId + "",
                videoId + "" ,userName});
        if (cursor.moveToFirst()) {
            hasVideo = true;
        }
        cursor.close();
        return hasVideo;
    }
    /**
     * 删除已经存在的视频记录
     */
    public boolean delVideoPlay(int chapterId, int videoId,String userName) {
        boolean delSuccess=false;
        int row = db.delete(SQLiteHelper.U_VIDEO_PLAY_LIST,
                " chapterId=? AND videoId=? AND userName=?", new String[] { chapterId + "",
                        videoId + "" ,userName});
        if (row > 0) {
            delSuccess=true;
        }
        return delSuccess;
    }

    public List<VideoBean> getVideoHistory(String userName){
        String sql="SELECT * FROM "+SQLiteHelper.U_VIDEO_PLAY_LIST+" WHERE userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        List<VideoBean> vb1=new ArrayList<VideoBean>();
        VideoBean bean=null;
        while (cursor.moveToNext()){
            bean=new VideoBean();
            bean.chapterId=cursor.getInt(cursor.getColumnIndex("chapterId"));
            bean.videoId=cursor.getInt(cursor.getColumnIndex("videoId"));
            bean.videoPath=cursor.getString(cursor.getColumnIndex("videoPath"));
            bean.title=cursor.getString(cursor.getColumnIndex("title"));
            bean.secondTitle=cursor.getString(cursor.getColumnIndex("secondTitle"));
            vb1.add(bean);
            bean=null;
        }
        cursor.close();
        return vb1;
    }
}
