package com.boxuegu.Activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
import com.boxuegu.bean.UserBean;
import com.boxuegu.utils.AnalysisUtils;
import com.boxuegu.utils.DBUtils;

import org.w3c.dom.Text;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView back;
    private TextView mainTitle;
    private TextView nickNameText;
    private TextView sexText;
    private TextView signatureText;
    private TextView userNameText;
    private RelativeLayout nickName, sex, signature, userName, titleBar;
    private String spUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName = AnalysisUtils.readLoginUserName(this);
        init();
        initData();
        setListener();
    }

    private void init() {
        back = (TextView) findViewById(R.id.back);
        mainTitle = (TextView) findViewById(R.id.mainTitle);
        mainTitle.setText("个人资料");
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30b4ff"));
        nickName = (RelativeLayout) findViewById(R.id.nickName);
        userName = (RelativeLayout) findViewById(R.id.userName);
        sex = (RelativeLayout) findViewById(R.id.sex);
        signatureText = (TextView) findViewById(R.id.signatureText);
        nickNameText = (TextView) findViewById(R.id.nickNameText);
        userNameText = (TextView) findViewById(R.id.userNameText);
        sexText = (TextView) findViewById(R.id.sexText);
        signatureText = (TextView) findViewById(R.id.signatureText);
    }

    private void initData() {
        UserBean userBean = null;
        userBean = DBUtils.getInstance(this).getUserInfo(spUserName);
        if (userBean == null) {
            userBean = new UserBean();
            userBean.userName = spUserName;
            userBean.nickName = "问答精灵";
            userBean.sex = "女";
            userBean.signature = "人家这么可爱，当然是男孩子啦";
            DBUtils.getInstance(this).saveUserInfo(userBean);
        }
        setValue(userBean);
    }

    private void setValue(UserBean userBean) {
        nickNameText.setText(userBean.nickName);
        userNameText.setText(userBean.userName);
        sexText.setText(userBean.sex);
        signatureText.setText(userBean.signature);
    }

    private void setListener() {
        back.setOnClickListener(this);
        nickName.setOnClickListener(this);
        userName.setOnClickListener(this);
        sex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.nickName:
                break;
            case R.id.userName:
                break;
            case R.id.sex:
                sexDialog(sexText.getText().toString());
                break;
            case R.id.signature:
                break;
            default:
                break;
        }
    }

    private void sexDialog(String sex) {
        int sexFlag = 0;
        if ("男".equals(sex)) {
            sexFlag = 0;
        } else if ("女".equals(sex)) {
            sexFlag = 1;
        }
        final String item[] = {"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(item, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,item[which],Toast.LENGTH_SHORT).show();
                setSex(item[which]);
            }
        });
        builder.create().show();
    }

    private void setSex(String sex){
        sexText.setText(sex);
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex",sex,spUserName);
    }
}
