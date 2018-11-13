package com.boxuegu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
    private static final int CHANGE_NICKNANE = 1;
    private static final int CHANGE_SIGNATURE = 2;
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
        signature= (RelativeLayout) findViewById(R.id.signature);
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
        signature.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.nickName:
                String name = nickNameText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("content", name);
                bundle.putString("title","昵称");
                bundle.putInt("flag", 1);
                enterActivityForResult(ChangeUserInfoActivity.class, CHANGE_NICKNANE, bundle);
                break;
            case R.id.signature:
                String signature = signatureText.getText().toString();
                Bundle signBundle = new Bundle();
                signBundle.putInt("flag", 2);
                signBundle.putString("title","签名");
                signBundle.putString("content", signature);
                enterActivityForResult(ChangeUserInfoActivity.class, CHANGE_SIGNATURE, signBundle);
                break;
            case R.id.userName:
                break;
            case R.id.sex:
                sexDialog(sexText.getText().toString());
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
                Toast.makeText(UserInfoActivity.this, item[which], Toast.LENGTH_SHORT).show();
                setSex(item[which]);
            }
        });
        builder.create().show();
    }

    private void setSex(String sex) {
        sexText.setText(sex);
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex", sex, spUserName);
    }

    public void enterActivityForResult(Class<?> to, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, to);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    private String new_info;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHANGE_NICKNANE:
                if (data != null) {
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    nickNameText.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName", new_info, spUserName);
                }
                break;
            case CHANGE_SIGNATURE:
                if (data != null) {
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    signatureText.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature", new_info, spUserName);
                }
                break;
            default:
                break;
        }
    }
}
