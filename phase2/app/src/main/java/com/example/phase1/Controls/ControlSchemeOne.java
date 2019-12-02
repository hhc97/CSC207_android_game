package com.example.phase1.Controls;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.BackendStorage.LevelActivity;
import com.example.phase1.R;

public class ControlSchemeOne extends GameManager {
  LevelActivity level;

  public ControlSchemeOne(LevelActivity level) {
    this.level = level;
  }

  public void setLeftButton() {
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

  public void setRightButton() {
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

  public void setAttackButton() {
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

  public void setJumpButton() {
    Button jump = level.findViewById(R.id.jump);
    jump.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                level.jumpAction();
              case MotionEvent.ACTION_UP:
            }
            // ... Respond to touch events
            return true;
          }
        });
  }
}
