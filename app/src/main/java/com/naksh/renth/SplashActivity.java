package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView renthLogo1 = findViewById(R.id.renthlogo);
        auth = FirebaseAuth.getInstance();


        // Scale animation for the logo
//        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim_two.xml.scale_animation);
//        renthLogo1.startAnimation(scaleAnimation);
        renthLogo1.animate().alpha(1f).setDuration(2000).start();
//
//        // Scale animation for the logo
//        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim_two.xml.scale_animation);
//        renthLogo1.startAnimation(scaleAnimation);
        // Slide in animation from the left
        Animation slideInLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        renthLogo1.startAnimation(slideInLeftAnimation);

//

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginScreen.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();            }
        }, 3500);
    }

//    private void finishWithSlideOut() {
//        ImageView renthLogo1 = findViewById(R.id.renthlogo);
//
//        // Slide out animation to the right
//        Animation slideOutRightAnimation = AnimationUtils.loadAnimation(this, R.anim_two.xml.slide_out_right);
//        renthLogo1.startAnimation(slideOutRightAnimation);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, LoginScreen.class);
//                startActivity(intent);
//                overridePendingTransition(android.R.anim_two.xml.fade_in, android.R.anim_two.xml.fade_out);
//                finish();
//            }
//        }, 1500); // Duration of slide out animation
//    }
}

//package com.naksh.renth;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//
//public class SplashActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ImageView renthLogo1=findViewById(R.id.renthlogo);
//
//        // Fade in animation for the logo
//        renthLogo1.animate().alpha(1f).setDuration(1500).start();
//
//        // Scale animation for the logo
//        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim_two.xml.scale_animation);
//        renthLogo1.startAnimation(scaleAnimation);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finishWithFadeOut();
//            }
//        }, 3500);
//    }
//
//    private void finishWithFadeOut() {
//        Intent intent = new Intent(this, LoginScreen.class);
//        startActivity(intent);
//        overridePendingTransition(android.R.anim_two.xml.fade_in, android.R.anim_two.xml.fade_out);
//        finish();
//    }
//}

