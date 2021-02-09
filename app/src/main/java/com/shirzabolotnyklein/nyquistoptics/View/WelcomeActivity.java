package com.shirzabolotnyklein.nyquistoptics.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.shirzabolotnyklein.nyquistoptics.Model.ConstantsKt;
import com.shirzabolotnyklein.nyquistoptics.R;

public class WelcomeActivity extends AppCompatActivity {

    /**
     * Seconds of Appearance welcome logo
     */
    private int SLEEP_TIMER =2;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
        getSupportActionBar().hide();
        imageView=findViewById(R.id.imageView);
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();


    }

    /**
     * Display the logo
     */
    private class LogoLauncher extends Thread {
        public void run() {
            try {

                Animation animation = new AlphaAnimation(0, 1); //to change visibility from visible to invisible
                animation.setDuration(2000); //1 second duration for each animation cycle
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
                animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
                imageView.startAnimation(animation); //to start animation
                sleep(ConstantsKt.OneThousand * SLEEP_TIMER);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}

