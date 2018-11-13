package com.boxuegu.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;

public class ChangeUserInfoActivity extends AppCompatActivity {

    private TextView mainTitle,save;
    private RelativeLayout titleBar;
    private TextView back;
    private String title,content;
    private EditText contentText;
    private ImageView deleteView;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        init();
    }

    private void init(){
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        flag=getIntent().getIntExtra("flag",0);
        mainTitle=(TextView) findViewById(R.id.mainTitle);
        mainTitle.setText(title);
        titleBar= (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B3FF"));
        back= (TextView) findViewById(R.id.back);
        save= (TextView) findViewById(R.id.save);
        save.setVisibility(View.VISIBLE);
        contentText= (EditText) findViewById(R.id.content);
        deleteView= (ImageView) findViewById(R.id.delete);
        if (!TextUtils.isEmpty(content)){
            contentText.setText(content);
            contentText.setSelection(content.length());
        }
        contentListener();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeUserInfoActivity.this.finish();
            }
        });
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentText.setText("");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                String contentValue=contentText.getText().toString().trim();
                switch (flag){
                    case 1:
                        if (!TextUtils.isEmpty(contentValue)){
                            data.putExtra("nickName",contentValue);
                            setResult(RESULT_OK,data);
                            Toast.makeText(ChangeUserInfoActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                            ChangeUserInfoActivity.this.finish();
                        }else {
                            Toast.makeText(ChangeUserInfoActivity.this,"昵称不能为空",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(contentValue)){
                            data.putExtra("signature",contentValue);
                            setResult(RESULT_OK,data);
                            Toast.makeText(ChangeUserInfoActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                            ChangeUserInfoActivity.this.finish();
                        }else {
                            Toast.makeText(ChangeUserInfoActivity.this,"签名不能为空",Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:break;
                }
            }
        });
    }

    private void contentListener(){
        contentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable=contentText.getText();
                int len=editable.length();
                if (len>0){
                    deleteView.setVisibility(View.VISIBLE);
                }else {
                    deleteView.setVisibility(View.GONE);
                }
                switch (flag){
                    case 1:
                        if (len>8){
                            int selEndIndex= Selection.getSelectionEnd(editable);
                            String str=editable.toString();
                            String newStr=str.substring(0,8);
                            contentText.setText(newStr);
                            editable=contentText.getText();
                            int newLen=editable.length();
                            if (selEndIndex>newLen){
                                selEndIndex=editable.length();
                            }
                            Selection.setSelection(editable,selEndIndex);
                        }
                        break;
                    case 2:
                        if (len>16){
                            int selEndIndex=Selection.getSelectionEnd(editable);
                            String str=editable.toString();
                            String newStr=str.substring(0,16);
                            contentText.setText(newStr);
                            editable=contentText.getText();
                            if (selEndIndex>editable.length()){
                                selEndIndex=editable.length();
                            }else {
                                Selection.setSelection(editable,selEndIndex);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
