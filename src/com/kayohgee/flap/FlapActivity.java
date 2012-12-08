package com.kayohgee.flap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class FlapActivity extends Activity {

    ToggleButton button;
    ImageView    pointer;
    AnimationSet animationSet = null;
    View touchShield;
    Handler handler;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (ToggleButton) findViewById(R.id.toggle_button);
        pointer = (ImageView) findViewById(R.id.pointer_hand);
        pointer.setVisibility(View.INVISIBLE);
        handler = new Handler();
        touchShield = findViewById(R.id.touch_shield);
        touchShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {}
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                touchShield.setVisibility(View.VISIBLE);
                pointer.setAnimation(animationSet);
                pointer.setVisibility(View.VISIBLE);
                animationSet.start();
            }
        });
    }

    @Override
    public View onCreateView (String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context,attrs);
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            if (animationSet == null){
                buildPointerAnimationSet();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        return false;
    }

    private void buildPointerAnimationSet () {

        ScaleAnimation scaleAnimationPressDown = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.8f,
                                                                    Animation.RELATIVE_TO_SELF, 0.5f,
                                                                    Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationPressDown.setStartOffset(1950);
        scaleAnimationPressDown.setDuration(150);

        ScaleAnimation scaleAnimationPressUp = new ScaleAnimation(1.0f, 1.25f, 1.0f, 1.25f,
                                                                  Animation.RELATIVE_TO_SELF, 0.5f,
                                                                  Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationPressUp.setStartOffset(2000);
        scaleAnimationPressUp.setDuration(150);

        final AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(500);
        fadeInAnimation.setFillAfter(true);

        final AlphaAnimation fadeOutAnimation = new AlphaAnimation(1, 0);
        fadeOutAnimation.setDuration(500);
        fadeOutAnimation.setFillAfter(true);

        int y_delta = -button.getTop() + 20;
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0,
                                                                       y_delta);
        translateAnimation.setStartOffset(500);
        translateAnimation.setDuration(1500);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setRepeatCount(1);

        animationSet = new AnimationSet(true);

        Animation.AnimationListener translateListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart (Animation animation) {}

            @Override
            public void onAnimationEnd (Animation animation) {
                pointer.setAnimation(fadeOutAnimation);
                fadeOutAnimation.start();
            }

            @Override
            public void onAnimationRepeat (Animation animation) {

                button.setPressed(true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.setPressed(false);
                        button.setChecked(false);
                    }
                }, 100);
            }
        };

        Animation.AnimationListener fadeOutListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart (Animation animation) {}

            @Override
            public void onAnimationEnd (Animation animation) {
                touchShield.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat (Animation animation) {}
        };
        translateAnimation.setAnimationListener(translateListener);
        fadeOutAnimation.setAnimationListener(fadeOutListener);
        animationSet.addAnimation(scaleAnimationPressUp);
        animationSet.addAnimation(scaleAnimationPressDown);
        animationSet.addAnimation(fadeInAnimation);
        animationSet.addAnimation(translateAnimation);
    }
}
