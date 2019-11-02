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
  private GifImageView hero;
  private GifImageView coin0;
  private GifImageView coin1;
  private GifImageView coin2;
  private GifImageView enemy;
  private Level1Manager manager;
  private int activityLevel; // Day or Night background
  private int[] heroAction = new int[4]; // animations for hero, stand, walk, hurt and attack
  private int[] enemyAction = new int[4]; // animations for enemy, stand, walk, hurt and attack
  private TextView scoreLabel;
  private TextView healthLabel;
  private int difficulty = 0;

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

    // calling initial setup methods
    setup();
    nullAction();
    setLeftButton();
    setRightButton();
    setAttackButton();
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
    Level1Manager.getPlayer().setHealth(getHealth());
    Level1Manager.getPlayer().setCoins(getCoin());
  }

  private void updateStatesToGameManager() {
    setHealth(Level1Manager.getPlayer().getHealth());
    setCoin(Level1Manager.getPlayer().getCoins());
    setScore(Level1Manager.getPlayer().getScore());
  }

  @SuppressLint("ClickableViewAccessibility")
  private void setLeftButton() {
    Button left = findViewById(R.id.left);
    left.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                leftAction();
              case MotionEvent.ACTION_UP:
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  @SuppressLint("ClickableViewAccessibility")
  private void setRightButton() {
    Button right = findViewById(R.id.right);
    right.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                rightAction();
              case MotionEvent.ACTION_UP:
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  @SuppressLint("ClickableViewAccessibility")
  private void setAttackButton() {
    Button attack = findViewById(R.id.attack);
    attack.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                attackAction();
              case MotionEvent.ACTION_UP:
               Level1Manager.getPlayer().notAttack();
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
    Level1Manager.getPlayer().attack();
    manager.update();
    nullAction();
    heroAttackAnimation();
    enemyHurtAnimation();
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
    updateImage();
    updateStatesToGameManager();
    checkIsWinning();
  }

  private void checkIsWinning() {
    boolean isWon = true;
    for (GameObject obj : manager.Objects) { // if all the GameObjects are dead
      if (obj.getStates()) isWon = false; // if any of them isn't, isWon = false
    }
    if (isWon) startNextLevel();
  }

  private void updateImage() {
    scoreLabel.setText("Score: " + getScore());
    healthLabel.setText("Health: " + getHealth());
    for (GameObject obj : manager.Objects) {
      obj.getImage().setX(obj.getX());
      if (!obj.getStates()) {
        imageInvisible(obj.getImage());
      }
    }
  }

  private void setup() {
    setActivityLevel();
    setContentView(activityLevel);
    manager = new Level1Manager();
    hero = findViewById(R.id.hero);
    Level1Manager.getPlayer().setImage(hero);
    enemy = findViewById(R.id.enemy);
    manager.Objects.get(0).setImage(enemy);
    coin0 = findViewById(R.id.c1);
    manager.Objects.get(1).setImage(coin0);
    coin1 = findViewById(R.id.c2);
    manager.Objects.get(2).setImage(coin1);
    coin2 = findViewById(R.id.c3);
    manager.Objects.get(3).setImage(coin2);
    scoreLabel = (TextView) findViewById(R.id.score2);
    healthLabel = (TextView) findViewById(R.id.health2);
    setHeroAction();
    setHeroStatus();
    setEnemyAction();
    setLevelDifficulty();
    scoreLabel.setText("Score: " + getScore());
    healthLabel.setText("Health: " + getHealth());
  }

  private void setLevelDifficulty() {
    this.difficulty = getDifficulty();
    if (this.difficulty == 0) {
      ((Monster) manager.Objects.get(0)).setHealth(1);
      ((Monster) manager.Objects.get(0)).setWorth(100);
    } else if (this.difficulty == 1) {
      ((Monster) manager.Objects.get(0)).setHealth(5);
      ((Monster) manager.Objects.get(0)).setWorth(200);
    } else if (this.difficulty == 2) {
      ((Monster) manager.Objects.get(0)).setHealth(10);
      ((Monster) manager.Objects.get(0)).setWorth(500);
    }
  }
}
