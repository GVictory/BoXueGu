package com.boxuegu.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
import com.boxuegu.utils.AnalysisUtils;
import com.boxuegu.utils.MD5Untils;

public class FindPswActivity extends AppCompatActivity {
    private EditText userName;
    private EditText validateName;
    private TextView resetPsw;
    private TextView back;
    private TextView title;
    private String from;
    private TextView validateNameText;
    private TextView userNameText;
    private Button validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        init();
    }

    private void init(){
        back= (TextView) findViewById(R.id.back);
        title= (TextView) findViewById(R.id.mainTitle);
        userName= (EditText) findViewById(R.id.userName);
        validateName= (EditText) findViewById(R.id.validateName);
        resetPsw= (TextView) findViewById(R.id.resetPsw);
        userNameText= (TextView) findViewById(R.id.userNameText);
        validateNameText= (TextView) findViewById(R.id.validateNameText);
        validate= (Button) findViewById(R.id.validate);
        from=getIntent().getStringExtra("from");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindPswActivity.this.finish();
            }
        });
        if ("security".equals(from)){
            title.setText("设置密保");
        }else {
            title.setText("找回密码");
            userNameText.setVisibility(View.VISIBLE);
            userName.setVisibility(View.VISIBLE);
        }
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validateNameValue=validateName.getText().toString().trim();
                if ("security".equals(from)){
                    if (TextUtils.isEmpty(validateNameValue)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_SHORT);
                        return;
                    }else {
                        Toast.makeText(FindPswActivity.this,"设置成功",Toast.LENGTH_SHORT);
                        saveSecurity(validateNameValue);
                        FindPswActivity.this.finish();
                    }
                }else {
                    String userNameValue=userName.getText().toString().trim();
                    String security=readSecurity(userNameValue);
                    if (TextUtils.isEmpty(userNameValue)){
                        Toast.makeText(FindPswActivity.this,"请输入您的用户名",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (!isExistUserName(userNameValue)){
                        Toast.makeText(FindPswActivity.this,"您输入的用户名不存在",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (TextUtils.isEmpty(validateNameValue)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (!validateNameValue.equals(security)){
                        Toast.makeText(FindPswActivity.this,"输入的密保不正确",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        resetPsw.setVisibility(View.VISIBLE);
                        resetPsw.setText("初始密码：123456");
                        savePsw(userNameValue);
                    }
                }
            }
        });
    }

    private void savePsw(String userName){
        String md5Psw= MD5Untils.getMD5String("123456");
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
    }

    private void saveSecurity(String validateName){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);
        editor.commit();
    }

    private String readSecurity(String userName){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sharedPreferences.getString(userName+"_security","");
    }

    private boolean isExistUserName(String username){
        boolean hasUserName=false;
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        if (!TextUtils.isEmpty(sharedPreferences.getString(username,""))){
            hasUserName=true;
        }else {
            hasUserName=false;
        }
        return hasUserName;
    }
}
