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

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import static com.boxuegu.utils.MD5Untils.getMD5String;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private EditText confirmPassword;
    private Button register;
    private TextView title;
    private TextView back;
    private String userNameValue,passwordValue,confirmPasswordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        title= (TextView) findViewById(R.id.mainTitle);
        userName= (EditText) findViewById(R.id.userName);
        password= (EditText) findViewById(R.id.password);
        confirmPassword= (EditText) findViewById(R.id.confirmPassword);
        register= (Button) findViewById(R.id.register);
        back= (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                RegisterActivity.this.finish();
            }
        });
        title.setText("注 册");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue= String.valueOf(userName.getText()).trim();
                passwordValue= String.valueOf(password.getText()).trim();
                confirmPasswordValue= String.valueOf(confirmPassword.getText());
                if (TextUtils.isEmpty(userNameValue)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(passwordValue)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else if (!passwordValue.equals(confirmPasswordValue)){
                    password.setText("");
                    confirmPassword.setText("");
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                }else if(IsExistName(userNameValue)) {
                    Toast.makeText(RegisterActivity.this,"用户名已被注册，请更换一个",Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences sharedPreferences=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(userNameValue,getMD5String(passwordValue));
                    editor.commit();
                    Toast.makeText(RegisterActivity.this,"注册成功，3秒后将跳转到登录页面",Toast.LENGTH_LONG).show();
                    Timer timer=new Timer();
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent=new Intent();
                            intent.putExtra("userName",userNameValue);
                            setResult(RESULT_OK,intent);
                            RegisterActivity.this.finish();
                        }
                    };
                    timer.schedule(timerTask,3000);
                }
            }
        });
    }

    private Boolean IsExistName(String userName){
        SharedPreferences preferences=getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String string=preferences.getString(userName,"");
        if (string.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
