package com.example.phase1.Controls;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.phase1.Animations.IAnimator;
import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.R;

public class ControlSchemeOne extends GameManager implements IController {

    @SuppressLint("ClickableViewAccessibility")
    public void moveLeft(final IAnimator animator){
        Button left = findViewById(R.id.left);
        left.setOnTouchListener(
                new RepeatListener(
                        100,
                        50,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                animator.facingLeft();
                            }
                        }));
    }
    @SuppressLint("ClickableViewAccessibility")
    public void moveRight(final IAnimator animator){
        Button right = findViewById(R.id.right);
        right.setOnTouchListener(
                new RepeatListener(
                        100,
                        50,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // the code to execute repeatedly
                                animator.facingRight();
                            }
                        }));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void attack(final IAnimator animator){
        final Button attack = findViewById(R.id.attack);
        attack.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getActionMasked();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                animator.attackAnimation();
                            case MotionEvent.ACTION_UP:
                        }
                        // ... Respond to touch events
                        return true;
                    }
                });
    }

}
