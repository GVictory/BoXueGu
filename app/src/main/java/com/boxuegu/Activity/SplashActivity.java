package com.boxuegu.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.boxuegu.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){
        TextView version=(TextView) findViewById(R.id.version);
        try {
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
            version.setText("V"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(timerTask,3000);
    }
}
