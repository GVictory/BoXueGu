package com.boxuegu.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.boxuegu.R;

public class FindPswActivity extends AppCompatActivity {
    private EditText userName;
    private EditText validateName;
    private TextView resetPsw;
    private TextView back;
    private TextView title;
    private String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
    }

    private void init(){
        back= (TextView) findViewById(R.id.back);
        title= (TextView) findViewById(R.id.mainTitle);
        userName= (EditText) findViewById(R.id.userName);
        validateName= (EditText) findViewById(R.id.validateName);
        resetPsw= (TextView) findViewById(R.id.resetPsw);
        from=getIntent().getStringExtra("from");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindPswActivity.this.finish();
            }
        });
    }
}
