package com.example.phase1.Level1Game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Monster;
import com.example.phase1.R;

import java.lang.reflect.Array;

import pl.droidsonroids.gif.GifImageView;

public class Level1Activity extends GameManager {

  private Level1Manager manager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra(sendPlayer, 0));
    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    // calling initial setup methods.
    manager = new Level1Manager();
    manager.setDayOrNight(getDayOrNight());
    manager.setDifficulty(getDifficulty());
    setHeroStatus();
    setLeftButton();
    setRightButton();
    setAttackButton();
    setJumpButton();
  }

  // Set health and coin statistics for the hero.
  private void setHeroStatus() {
    manager.getPlayer().setHealth(getHealth());
    manager.getPlayer().setCoins(getCoin());
  }

  // Update states in Game Manager Class.
  private void updateStatesToGameManager() {
    setHealth(manager.getPlayer().getHealth());
    setCoin(manager.getPlayer().getCoins());
    setScore(manager.getPlayer().getScore());
  }

  // Create and set event listener for left button.
  private void setLeftButton() {
    Button left = findViewById(R.id.left);
    left.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                manager.leftButtomPress();
                updateStatesToGameManager();
                checkIsWinning();
              case MotionEvent.ACTION_UP:
            }
            // Respond to touch events.
            return true;
          }
        });
  }

  // Create and set event listener for right button
  @SuppressLint("ClickableViewAccessibility")
  private void setRightButton() {
    Button right = findViewById(R.id.right);
    right.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                manager.rightButtomPress();
                updateStatesToGameManager();
                checkIsWinning();
              case MotionEvent.ACTION_UP:
            }
            // ... Respond to touch events
            return true;
          }
        });
  }
  // Create and set event listener for attack button
  @SuppressLint("ClickableViewAccessibility")
  private void setAttackButton() {
    Button attack = findViewById(R.id.attack);
    attack.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                manager.attackButtomPress();
                updateStatesToGameManager();
                checkIsWinning();
              case MotionEvent.ACTION_UP:
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  private void setJumpButton() {}
  private void checkIsWinning(){
    if(manager.isWinning()){
      startNextLevel();
    }
  }
}
