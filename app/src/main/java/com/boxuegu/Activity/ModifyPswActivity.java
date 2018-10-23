package com.boxuegu.Activity;

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
import com.boxuegu.utils.AnalysisUtils;
import com.boxuegu.utils.MD5Untils;

public class ModifyPswActivity extends AppCompatActivity {
    private EditText originalPsw;
    private EditText newPsw;
    private EditText comfirmPsw;
    private Button save;
    private TextView title;
    private TextView back;
    private String originalPswValue,newPswValue,comfirmPswValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        init();
    }

    private void init(){
        originalPsw= (EditText) findViewById(R.id.originalPsw);
        newPsw= (EditText) findViewById(R.id.newPsw);
        comfirmPsw= (EditText) findViewById(R.id.confirmPsw);
        save= (Button) findViewById(R.id.save);
        title= (TextView) findViewById(R.id.mainTitle);
        back= (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPswActivity.this.finish();
            }
        });
        title.setText("修改密码");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if (TextUtils.isEmpty(originalPswValue)){
                    Toast.makeText(ModifyPswActivity.this,"请输入原始密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!MD5Untils.getMD5String(originalPswValue).equals(readPsw())){
                    Toast.makeText(ModifyPswActivity.this,"输入的密码与原密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(newPswValue)){
                    Toast.makeText(ModifyPswActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(comfirmPswValue)){
                    Toast.makeText(ModifyPswActivity.this,"请再次输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (MD5Untils.getMD5String(newPswValue).equals(readPsw())){
                    Toast.makeText(ModifyPswActivity.this,"输入的密码不能与原密码一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!newPswValue.equals(comfirmPswValue)){
                    Toast.makeText(ModifyPswActivity.this,"两次输入的新密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    modifyPsw(newPswValue);
                    Toast.makeText(ModifyPswActivity.this,"新密码设置成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ModifyPswActivity.this,LoginActivity.class);
                    startActivity(intent);
                    SettingActivity.instance.finish();
                    ModifyPswActivity.this.finish();
                }
            }
        });
    }

    private void getEditString(){
        originalPswValue=originalPsw.getText().toString().trim();
        newPswValue=newPsw.getText().toString().trim();
        comfirmPswValue=comfirmPsw.getText().toString().trim();
    }

    private void modifyPsw(String newPsw){
        String md5Psw=MD5Untils.getMD5String(newPsw);
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this),md5Psw);
        editor.commit();
    }

    private String readPsw(){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sharedPreferences.getString(AnalysisUtils.readLoginUserName(this),"");
    }


}
