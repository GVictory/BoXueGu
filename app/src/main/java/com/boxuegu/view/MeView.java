package com.boxuegu.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.Activity.LoginActivity;
import com.boxuegu.Activity.SettingActivity;
import com.boxuegu.R;
import com.boxuegu.utils.AnalysisUtils;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2018/10/16 0016.
 */

public class MeView {
    public ImageView headIcon;
    private LinearLayout head;
    private RelativeLayout courseHistory,setting;
    private TextView userName;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View  mCurrentView;

     public MeView(Activity context){
         mContext=context;
         mInflater=LayoutInflater.from(mContext);
     }

     private void createView(){
         initView();
     }

     private void initView(){
         mCurrentView=mInflater.inflate(R.layout.main_view_me,null);
         head= (LinearLayout) mCurrentView.findViewById(R.id.head);
         headIcon= (ImageView) mCurrentView.findViewById(R.id.headIcon);
         courseHistory= (RelativeLayout) mCurrentView.findViewById(R.id.courseHistory);
         setting= (RelativeLayout) mCurrentView.findViewById(R.id.setting);
         userName= (TextView) mCurrentView.findViewById(R.id.userName);
         mCurrentView.setVisibility(View.VISIBLE);
         setLoginParams(readLoginStatus());
         head.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (readLoginStatus()){

                 }else{
                     Intent intent=new Intent(mContext, LoginActivity.class);
                     mContext.startActivityForResult(intent,1);
                 }
             }
         });
         courseHistory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (readLoginStatus()){

                 }else {
                     Toast.makeText(mContext, "您还未登录，请进行登录", Toast.LENGTH_SHORT).show();
                 }
             }
         });
         setting.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (readLoginStatus()){
                     Intent intent=new Intent(mContext, SettingActivity.class);
                     mContext.startActivityForResult(intent,1);
                 }else {
                     Toast.makeText(mContext, "您还未登录，请进行登录", Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }

     public void setLoginParams(boolean isLogin){
         if (isLogin){
             userName.setText(AnalysisUtils.readLoginUserName(mContext));
         }else {
             userName.setText("点击登录");
         }
     }

     public View getView(){
         if (mCurrentView==null){
             createView();
         }
         return mCurrentView;
     }

     public void showView(){
         if (mCurrentView==null){
             createView();
         }
         mCurrentView.setVisibility(View.VISIBLE);
     }

     private boolean readLoginStatus(){
         SharedPreferences sharedPreferences=mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
         boolean isLogin=sharedPreferences.getBoolean("isLogin",false);
         return isLogin;
     }
}
