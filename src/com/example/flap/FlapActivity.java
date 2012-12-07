package com.example.flap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class FlapActivity extends Activity {

    ToggleButton button;
    ImageView    pointer;
    AnimationSet animationSet = null;
    View touchShield;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (ToggleButton) findViewById(R.id.toggle_button);
        pointer = (ImageView) findViewById(R.id.pointer_hand);
        pointer.setVisibility(View.INVISIBLE);
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
                button.setChecked(false);
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
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(fadeInAnimation);
    }
}
