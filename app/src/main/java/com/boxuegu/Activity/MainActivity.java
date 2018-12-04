package com.boxuegu.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boxuegu.R;
import com.boxuegu.view.ExercisesView;
import com.boxuegu.view.MeView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
//    标题栏组件
    private RelativeLayout titleBar;
    private TextView title;
    private TextView back;
//    导航栏组件
    private View courseView;
    private ImageView courseImage;
    private TextView courseText;
    private View exercisesView;
    private ImageView exercisesImage;
    private TextView exercisesText;
    private View meView;
    private ImageView meImage;
    private TextView meText;
//    中间视图
    private FrameLayout bodyLayout;
//    导航栏视图
    private LinearLayout bottomBar;
    private MeView myView;
    private ExercisesView mExercisesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListener();
        setInitStatus();
    }

    private void init(){
        bodyLayout= (FrameLayout) findViewById(R.id.BodyLayout);
        bottomBar= (LinearLayout) findViewById(R.id.bottomBar);
        titleBar= (RelativeLayout) findViewById(R.id.title_bar);
        title= (TextView) findViewById(R.id.mainTitle);
        back= (TextView) findViewById(R.id.back);
        courseView=findViewById(R.id.courseView);
        courseImage= (ImageView) findViewById(R.id.courseImage);
        courseText= (TextView) findViewById(R.id.courserText);
        exercisesView=findViewById(R.id.exercisesView);
        exercisesImage= (ImageView) findViewById(R.id.exercisesImage);
        exercisesText= (TextView) findViewById(R.id.exercisesText);
        meView=findViewById(R.id.meView);
        meImage= (ImageView) findViewById(R.id.meImage);
        meText= (TextView) findViewById(R.id.meText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.courseView:
                clearBottomIamgeStage();
                selectDisplayView(0);
                break;
            case R.id.exercisesView:
                clearBottomIamgeStage();
                selectDisplayView(1);
                break;
            case R.id.meView:
                clearBottomIamgeStage();
                selectDisplayView(2);
                break;
        }
    }

    private void setListener(){
        for (int i=0;i<bottomBar.getChildCount();i++){
            bottomBar.getChildAt(i).setOnClickListener(this);
        }
    }

    private void clearBottomIamgeStage(){
        courseText.setTextColor(Color.parseColor("#666666"));
        exercisesText.setTextColor(Color.parseColor("#666666"));
        meText.setTextColor(Color.parseColor("#666666"));
        courseImage.setImageResource(R.drawable.main_course_icon);
        exercisesImage.setImageResource(R.drawable.main_exercises_icon);
        meImage.setImageResource(R.drawable.main_my_icon);
        for (int i=0;i<bottomBar.getChildCount();i++){
            bottomBar.getChildAt(i).setSelected(false);
        }
    }

    private void setSelectedStatus(int index){
        switch (index){
            case 0:
                courseText.setTextColor(Color.parseColor("#0097F7"));
                courseImage.setImageResource(R.drawable.main_course_icon_selected);
                title.setText("博学谷课程");
                titleBar.setVisibility(View.VISIBLE);
                break;
            case 1:
                exercisesText.setTextColor(Color.parseColor("#0097F7"));
                exercisesImage.setImageResource(R.drawable.main_exercises_icon_selected);
                title.setText("博学谷习题");
                titleBar.setVisibility(View.VISIBLE);
                break;
            case 2:
                meText.setTextColor(Color.parseColor("#0097F7"));
                meImage.setImageResource(R.drawable.main_my_icon_selected);
                titleBar.setVisibility(View.GONE);
                break;
            default:break;
        }
    }

    private void removeAllView(){
        for (int i=0;i<bodyLayout.getChildCount();i++){
            bodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    private void setInitStatus(){
        clearBottomIamgeStage();
        setSelectedStatus(0);
        createView(0);
    }

    private void selectDisplayView(int index){
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }

    private void createView(int index){
        switch (index){
            case 0:

                break;
            case 1:
                if (mExercisesView==null){
                    mExercisesView=new ExercisesView(this);
                    bodyLayout.addView(mExercisesView.getView());
                }else {
                    mExercisesView.getView();
                }
                mExercisesView.showView();
                break;
            case 2:
                if(myView==null) {
                    myView = new MeView(this);
                    bodyLayout.addView(myView.getView());
                }else {
                    myView.getView();
                }
                myView.showView();
                break;
        }
    }

    protected long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(MainActivity.this,"再按一次退出博学谷",Toast.LENGTH_LONG).show();
                exitTime= System.currentTimeMillis();
            }else {
                MainActivity.this.finish();
                if (readLoginStatus()){
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean readLoginStatus(){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sharedPreferences.getBoolean("isLogin",false);
        return isLogin;
    }

    private void clearLoginStatus(){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            boolean isLogin=data.getBooleanExtra("isLogin",false);
            if (isLogin){
                clearBottomIamgeStage();
                selectDisplayView(0);
            }
            if (myView!=null){
                myView.setLoginParams(isLogin);
            }
        }
    }
}
