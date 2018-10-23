package com.boxuegu.Activity;

import android.content.Context;
import android.content.Intent;
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
import com.boxuegu.utils.MD5Untils;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private TextView register;
    private TextView forgetPassword;
    private TextView title;
    private TextView back;
    private Button login;
    private String userNameValue;
    private String passwordValue;
    private String conformPasswordValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        userName= (EditText) findViewById(R.id.userName);
        password= (EditText) findViewById(R.id.password);
        register= (TextView) findViewById(R.id.register);
        login= (Button) findViewById(R.id.login);
        forgetPassword= (TextView) findViewById(R.id.forgetPassword);
        title= (TextView) findViewById(R.id.mainTitle);
        back= (TextView) findViewById(R.id.back);
        title.setText("登 录");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                LoginActivity.this.finish();
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,FindPswActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue=userName.getText().toString().trim();
                passwordValue=password.getText().toString().trim();
                conformPasswordValue=getPasswordByUser(userNameValue);
                if (TextUtils.isEmpty(userNameValue)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(passwordValue)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if (!TextUtils.isEmpty(conformPasswordValue)&&conformPasswordValue.equals(MD5Untils.getMD5String(passwordValue))){
                    saveLoginStatus(true,userNameValue);
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("isLogin",true);
                    setResult(RESULT_OK,intent);
                    LoginActivity.this.finish();
                }else if (!TextUtils.isEmpty(conformPasswordValue)&&!conformPasswordValue.equals(MD5Untils.getMD5String(passwordValue))){
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getPasswordByUser(String userName){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString(userName,"");
    }

    private void saveLoginStatus(Boolean status,String userNameValue){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("loginUserName",userNameValue);
        editor.putBoolean("status",status);
        editor.putBoolean("isLogin",status);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String registerUserName=data.getStringExtra("userName");
        if (!TextUtils.isEmpty(registerUserName)){
            userName.setText(registerUserName);
            userName.setSelection(registerUserName.length());
        }

    }
}
