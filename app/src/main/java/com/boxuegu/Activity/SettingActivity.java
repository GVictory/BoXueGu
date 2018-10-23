package com.boxuegu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {

    private TextView title;
    private TextView back;
    private RelativeLayout titleBar;
    private RelativeLayout motifyPassword;
    private RelativeLayout securitySetting;
    private RelativeLayout exitLogin;
    public static SettingActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init(){
        motifyPassword= (RelativeLayout) findViewById(R.id.motifyPassword);
        securitySetting= (RelativeLayout) findViewById(R.id.securitySetting);
        exitLogin= (RelativeLayout) findViewById(R.id.exitLogin);
        back= (TextView) findViewById(R.id.back);
        title= (TextView) findViewById(R.id.mainTitle);
        instance=this;
        titleBar= (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        title.setText("设 置");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        motifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,ModifyPswActivity.class);
                startActivity(intent);
            }
        });

        securitySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,FindPswActivity.class);
                intent.putExtra("from","security");
                startActivity(intent);
            }
        });

        exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this,"成功退出登录",Toast.LENGTH_LONG);
                clearLoginStatus();
                Intent intent=new Intent();
                intent.putExtra("isLogin",false);
                setResult(RESULT_OK,intent);
                SettingActivity.this.finish();
            }
        });
    }

    private void clearLoginStatus(){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("loginUserName","");
        editor.putBoolean("isLogin",false);
        editor.commit();
    }

}
