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
    TranslateAnimation translateAnimation = null;
    AlphaAnimation     fadeInAnimation    = null;
    AlphaAnimation     fadeOutAnimation   = null;
    AnimationSet       animationSet       = null;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button = (ToggleButton) findViewById(R.id.toggle_button);

        pointer = (ImageView) findViewById(R.id.pointer_hand);
        pointer.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
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
        fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(500);
        fadeInAnimation.setFillAfter(true);
        fadeOutAnimation = new AlphaAnimation(1, 0);
        fadeOutAnimation.setDuration(500);
        fadeOutAnimation.setFillAfter(true);
        translateAnimation = new TranslateAnimation(0, 0, 0, -button.getTop() + 20);
        translateAnimation.setStartOffset(500);
        translateAnimation.setDuration(1500);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setRepeatCount(1);
        animationSet = new AnimationSet(true);
        Animation.AnimationListener listener = new Animation.AnimationListener() {
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
        translateAnimation.setAnimationListener(listener);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(fadeInAnimation);
    }
}
