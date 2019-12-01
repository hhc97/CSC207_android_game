package com.example.phase2.Controls;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.phase2.Animations.IAnimator;
import com.example.phase2.BackendStorage.GameManager;
import com.example.phase2.Level1Game.Level1Activity;
import com.example.phase1.R;

public class ControlSchemeOne extends GameManager implements IController {

    public void setLeftButton(final Level1Activity level) {
        Button left = level.findViewById(R.id.left);
        left.setOnTouchListener(
                new RepeatListener(
                        100,
                        50,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                level.leftAction();
                            }
                        }));
    }

    public void setRightButton(final Level1Activity level) {
        Button right = level.findViewById(R.id.right);
        right.setOnTouchListener(
                new RepeatListener(
                        100,
                        50,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                level.rightAction();
                            }
                        }));
    }

    public void setAttackButton(final Level1Activity level) {
        Button attack = level.findViewById(R.id.attack);
        attack.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getActionMasked();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                level.attackAction();
                            case MotionEvent.ACTION_UP:
                        }
                        // ... Respond to touch events
                        return true;
                    }
                });
    }
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
