package com.example.elibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elibrary.R;
import com.example.elibrary.helpers.AppActivity;

public class SplashScreen extends AppActivity {
    private TextView splashtext;
    private ImageView splashimage;
    private Animation animation;
    private Animation animation1;
    private Animation animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initializedView();
        initializedListners();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

    @Override
    protected void initializedView() {
        splashtext=findViewById(R.id.splash_text);
        splashimage=findViewById(R.id.splash_image);

    }

    @Override
    protected void initializedListners() {
        animation= AnimationUtils.loadAnimation(this,R.anim.splashscreen_anim);
        animation1=AnimationUtils.loadAnimation(this,R.anim.splashscreen_anim1);
        animation2=AnimationUtils.loadAnimation(this,R.anim.splashscreen_anim2);


//        splashtext.setAnimation(animation);
        splashtext.setAnimation(animation1);
        splashimage.setAnimation(animation2);


    }
}
