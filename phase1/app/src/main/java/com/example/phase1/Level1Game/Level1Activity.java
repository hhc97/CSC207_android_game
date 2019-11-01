package com.example.phase1.Level1Game;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Monster;
import com.example.phase1.R;

import java.lang.reflect.Array;

import pl.droidsonroids.gif.GifImageView;

public class Level1Activity extends GameManager {
  private GifImageView hero;
  private GifImageView coin0;
  private GifImageView coin1;
  private GifImageView coin2;
  private GifImageView enemy;
  private Level1Manager manager;
  private int activityLevel; // Day or Night background
  private int[] heroAction = new int[4]; // animations for hero, stand, walk, hurt and attack
  private int[] enemyAction = new int[4]; // animations for enemy, stand, walk, hurt and attack
  boolean isAttack = false;
  boolean isMoveRight = false;
  boolean isMoveLeft = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra(sendPlayer, 0));
    this.isAttack = false;
    this.isMoveRight = false;
    this.isMoveLeft = false;
    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setActivityLevel();
    setContentView(activityLevel);
    hero = findViewById(R.id.hero);
    coin0 = findViewById(R.id.c1);
    coin1 = findViewById(R.id.c2);
    coin2 = findViewById(R.id.c3);
    enemy = findViewById(R.id.enemy);
    manager = new Level1Manager();

    setHeroAction();
    setHeroStatus();
    setEnemyAction();
    heroStandAnimation();

    enemy.setX(manager.Objects.get(0).getX());
    coin0.setX(manager.Objects.get(1).getX());
    coin1.setX(manager.Objects.get(2).getX());
    coin2.setX(manager.Objects.get(3).getX());

    // Move Graphic Components Right if Left Button is Pressed
    setLeftButton();

    // Move Graphic Components Left if Right Button is Pressed
    setRightButton();

    setAttackButton();

    Thread t =
        new Thread() {
          public void run() {
            while (manager.player.getStates() && !checkIsWinning()) {
              checkIsWinning();
              updateHealthToGameManager();
              manager.update();
              nullAction();
              if (isAttack) {
                attackAction();
                try {
                  sleep(100);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                break;
              }
              if (isMoveLeft) {
                leftAction();
                try {
                  sleep(100);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                break;
              }
              if (isMoveRight) {
                rightAction();
                try {
                  sleep(100);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                break;
              }
              try {
                sleep(250);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              updateHealthToGameManager();
              System.out.println("loop working");
            }
          }
        };
    t.start();
  }

  private void setActivityLevel() {
    int DayOrNight = getDayOrNight();
    if (DayOrNight == 0) activityLevel = R.layout.n_activity_level1;
    else if (DayOrNight == 1) activityLevel = R.layout.activity_level1;
  }

  private void setHeroAction() {
    int heroType = getCharacter();
    if (heroType == 0) {
      Array.set(heroAction, 0, R.drawable.hero1); // stand/unmoved
      Array.set(heroAction, 1, R.drawable.walk1); // walk animation
      Array.set(heroAction, 2, R.drawable.hurt1); // get hurt animation
      Array.set(heroAction, 3, R.drawable.attack1); // attack animation
    } else if (heroType == 1) {
      Array.set(heroAction, 0, R.drawable.knight); // stand/unmoved
      Array.set(heroAction, 1, R.drawable.walk); // walk animation
      Array.set(heroAction, 2, R.drawable.hurt2); // get hurt animation
      Array.set(heroAction, 3, R.drawable.attack); // attack animation
    }
  }

  private void setEnemyAction() {
    int enemyType = 0; // default, since we only have one type of enemy right now
    if (enemyType == 0) {
      Array.set(enemyAction, 0, R.drawable.mage); // stand/unmoved
      Array.set(enemyAction, 1, R.drawable.walke); // walk animation
      Array.set(enemyAction, 2, R.drawable.walke); // get hurt animation
      Array.set(enemyAction, 3, R.drawable.e_attack); // attack animation
    }
  }

  private void heroStandAnimation() {
    hero.setImageResource(heroAction[0]);
  }

  private void heroWalkAnimation() {
    hero.setImageResource(heroAction[1]);
  }

  private void heroHurtAnimation() {
    hero.setImageResource(heroAction[2]);
  }

  private void heroAttackAnimation() {
    hero.setImageResource(heroAction[3]);
  }

  private void heroFacingLeft() {
    hero.setScaleX(-1f);
  }

  private void heroFacingRight() {
    hero.setScaleX(1f);
  }

  private void enemyStandAnimation() {
    enemy.setImageResource(enemyAction[0]);
  }

  private void enemyWalkAnimation() {
    enemy.setImageResource(enemyAction[1]);
  }

  private void enemyHurtAnimation() {
    enemy.setImageResource(enemyAction[2]);
  }

  private void enemyAttackAnimation() {
    enemy.setImageResource(enemyAction[3]);
  }

  private void enemyFacingLeft() {
    enemy.setScaleX(-1f);
  }

  private void enemyFacingRight() {
    enemy.setScaleX(1f);
  }

  private void imageInvisible(GifImageView image) {
    image.setVisibility(View.INVISIBLE);
  }

  private void setHeroStatus() {
    manager.player.setHealth(getHealth());
    manager.player.setCoins(getCoin());
  }

  private void updateHealthToGameManager() {
    setHealth(manager.player.getHealth());
    setCoin(manager.player.getCoins());
  }

  private void setLeftButton() {
    Button left = findViewById(R.id.left);
    left.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                leftAction();
                isMoveLeft = true;
              case MotionEvent.ACTION_UP:
                isMoveLeft = false;
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  private void setRightButton() {
    Button right = findViewById(R.id.right);
    right.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                isMoveRight = true;
                rightAction();
              case MotionEvent.ACTION_UP:
                isMoveRight = false;
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  private void setAttackButton() {
    Button attack = findViewById(R.id.attack);
    attack.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                isAttack = true;
                attackAction();
              case MotionEvent.ACTION_UP:
                try {
                  Thread.sleep(50);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                isAttack = false;
                manager.player.notAttack();
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  private void setJumpButton() {}

  private void rightAction() {
    heroFacingRight();
    hero.setX(manager.heroMoveRight());
    heroWalkAnimation();
    nullAction();
  }

  private void leftAction() {
    hero.setX(manager.heroMoveLeft());
    heroFacingLeft();
    heroWalkAnimation();
    nullAction();
  }

  private void attackAction() {
    manager.player.attack();
    manager.update();
    nullAction();
    heroAttackAnimation();
  }

  private void nullAction() {

      manager.update();
    if (((Monster) manager.Objects.get(0)).isMoveLeft()) {
      enemyFacingLeft();
    } else {
      enemyFacingRight();
    }

    if (((Monster) manager.Objects.get(0)).isAttack() && enemy.isShown()) {
      enemyAttackAnimation();
      heroHurtAnimation();
    } else {
      enemyWalkAnimation();
      heroWalkAnimation();
    }
    manager.update();
    enemy.setX(manager.Objects.get(0).getX());
    coin0.setX(manager.Objects.get(1).getX());
    coin1.setX(manager.Objects.get(2).getX());
    coin2.setX(manager.Objects.get(3).getX());
    if (!manager.Objects.get(0).getStates()) {
      imageInvisible(enemy);
    }
    if (!manager.Objects.get(1).getStates()) {
      imageInvisible(coin0);
    }
    if (!manager.Objects.get(2).getStates()) {
      imageInvisible(coin1);
    }
    if (!manager.Objects.get(3).getStates()) {
      imageInvisible(coin2);
    }
    checkIsWinning();
  }

  private boolean checkIsWinning() {
    for (GameObject obj : manager.Objects) {
      if (obj.getStates()) return false;
    }
    super.startNextLevel();
    return true;
  }
}
