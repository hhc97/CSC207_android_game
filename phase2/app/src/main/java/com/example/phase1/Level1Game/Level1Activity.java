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
import java.util.ArrayList;
import java.util.logging.Level;

import pl.droidsonroids.gif.GifImageView;

public class Level1Activity extends GameManager {

  private Level1Manager manager;
  private GifImageView hero;
  private GifImageView coin0;
  private GifImageView coin1;
  private GifImageView coin2;
  private GifImageView enemy0;
  private GifImageView enemy1;
  private GifImageView enemy2;
  private int activityLevel; // Day or Night background.
  private int[] heroAction = new int[4]; // Animations for hero, stand, walk, hurt and attack.
  private int[] enemyAction = new int[4]; // Animations for enemy, stand, walk, hurt and attack.
  private TextView scoreLabel;
  private TextView healthLabel;
  private int difficulty = 0; // default difficulty
  private int dayOrNight = 0; // default difficulty
  private ArrayList<GameObject> Objects;

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
    setup();
    nullAction();
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
        new RepeatListener(
            100,
            50,
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                manager.leftButtonPress();
                leftAction();
                updateStatesToGameManager();
                checkIsWinning();
              }
            }));
  }

  // Create and set event listener for right button
  @SuppressLint("ClickableViewAccessibility")
  private void setRightButton() {
    Button right = findViewById(R.id.right);
    right.setOnTouchListener(
        new RepeatListener(
            100,
            50,
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                // the code to execute repeatedly
                manager.rightButtonPress();
                rightAction();
                updateStatesToGameManager();
                checkIsWinning();
              }
            }));
  }
  // Create and set event listener for attack button
  @SuppressLint("ClickableViewAccessibility")
  private void setAttackButton() {
    final Button attack = findViewById(R.id.attack);
    attack.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                manager.attackButtonPress();
                attackAction();
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

  private void checkIsWinning() {
    if (manager.isWinning()) {
      startNextLevel();
      finish();
    }
  }

  // Move right when right button pressed
  public void rightAction() {
    updateImage();
    heroFacingRight();
    heroWalkAnimation();
    nullAction();
  }

  // Move left when left button pressed
  public void leftAction() {
    updateImage();
    heroFacingLeft();
    heroWalkAnimation();
    nullAction();
  }

  // Attack when attack button is pressed
  public void attackAction() {
    updateImage();
    nullAction();
    heroAttackAnimation();
    for (int i = 1; i <= 3; i++) {
      if (((Monster) Objects.get(i)).isGetHit()) enemyHurtAnimation(i);
    }
  }

  public void jumpAction() {}

  private void nullAction() {

    for (int i = 1; i <= 3; i++) {
      if (((Monster) Objects.get(i)).isMoveLeft()) {
        enemyFacingLeft(i);
      } else {
        enemyFacingRight(i);
      }

      if (((Monster) Objects.get(i)).isAttack() && Objects.get(i).getStates()) {
        enemyAttackAnimation(i);
        heroHurtAnimation();
      } else {
        enemyWalkAnimation(i);
      }
    }
  }

  private void updateImage() {
    scoreLabel.setText("Score: " + getScore());
    healthLabel.setText("Health: " + getHealth());
    for (GameObject obj : Objects) {
      obj.getImage().setX(obj.getX());
      if (!obj.getStates()) {
        imageInvisible(obj.getImage());
      }
    }
  }

  // Reference to all front end objects from back
  private void setup() {
    this.difficulty = getDifficulty();
    this.dayOrNight = getDayOrNight();
    manager = new Level1Manager(this.difficulty);
    manager.setDayOrNight(this.dayOrNight);
    manager.setDifficulty(this.difficulty);
    this.Objects = manager.getObjects();
    setActivityLevel();
    setContentView(activityLevel);
    hero = findViewById(R.id.hero);
    Objects.get(0).setImage(hero);
    enemy0 = findViewById(R.id.enemy);
    Objects.get(1).setImage(enemy0);
    enemy1 = findViewById(R.id.enemy2);
    Objects.get(2).setImage(enemy1);
    enemy2 = findViewById(R.id.enemy3);
    Objects.get(3).setImage(enemy2);
    coin0 = findViewById(R.id.c1);
    Objects.get(4).setImage(coin0);
    coin1 = findViewById(R.id.c2);
    Objects.get(5).setImage(coin1);
    coin2 = findViewById(R.id.c3);
    Objects.get(6).setImage(coin2);
    scoreLabel = (TextView) findViewById(R.id.score2);
    healthLabel = (TextView) findViewById(R.id.health2);
    setHeroAction();
    setEnemyAction();
    scoreLabel.setText("Score: " + getScore());
    healthLabel.setText("Health: " + getHealth());
    setAllObjectsToVisible();
    updateImage();
    nullAction();
    heroStandAnimation();
  }

  // Set the day or night layout depending on user choice from Game Manager.
  private void setActivityLevel() {
    if (dayOrNight == 0) activityLevel = R.layout.n_activity_level1;
    else if (dayOrNight == 1) activityLevel = R.layout.activity_level1;
  }

  // Set the hero sprites based on user choice from Game Manager.
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

  // Set the walking and attacking actions for the enemy sprite.
  private void setEnemyAction() {
    int enemyType = 0; // Default, since we only have one type of enemy right now.
    if (enemyType == 0) {
      Array.set(enemyAction, 0, R.drawable.mage); // stand/unmoved
      Array.set(enemyAction, 1, R.drawable.walke); // walk animation
      Array.set(enemyAction, 2, R.drawable.e_hurt); // get hurt animation
      Array.set(enemyAction, 3, R.drawable.e_attack); // attack animation
    }
  }

  // Set the default standing animation for the hero.
  private void heroStandAnimation() {
    hero.setImageResource(heroAction[0]);
  }

  // Set the walking animation for hero sprite.
  private void heroWalkAnimation() {
    hero.setImageResource(heroAction[1]);
  }

  // Show hero being hurt when it is attacked by enemy.
  private void heroHurtAnimation() {
    hero.setImageResource(heroAction[2]);
  }

  // Show the hero move his weapon when attacking.
  private void heroAttackAnimation() {
    hero.setImageResource(heroAction[3]);
  }

  // Flip the hero sprite left.
  private void heroFacingLeft() {
    hero.setScaleX(-1f);
  }

  // Flip the hero sprite right.
  private void heroFacingRight() {
    hero.setScaleX(1f);
  }

  // Set default standing animation for the Enemy.
  private void enemyStandAnimation(int index) {
    Objects.get(index).getImage().setImageResource(enemyAction[0]);
  }

  // Set the walking animation for enemy sprite.
  private void enemyWalkAnimation(int index) {
    Objects.get(index).getImage().setImageResource(enemyAction[1]);
  }

  // Show enemy getting hurt when hit by enemy.
  private void enemyHurtAnimation(int index) {
    Objects.get(index).getImage().setImageResource(enemyAction[2]);
  }

  // Show enemy moving weapon and attacking hero.
  private void enemyAttackAnimation(int index) {
    Objects.get(index).getImage().setImageResource(enemyAction[3]);
  }

  // Flip enemy sprite left.
  private void enemyFacingLeft(int index) {
    Objects.get(index).getImage().setScaleX(-1f);
  }
  // Flip enemy sprite right.
  private void enemyFacingRight(int index) {
    Objects.get(index).getImage().setScaleX(1f);
  }
  // Set gif to invisible.
  private void imageInvisible(GifImageView image) {
    image.setVisibility(View.INVISIBLE);
  }
  // Set gif to visible
  private void imageVisible(GifImageView image) {
    image.setVisibility(View.VISIBLE);
  }
  // Make sure every image of the object is visible before the game
  private void setAllObjectsToVisible() {
    for (GameObject obj : Objects) {
      imageVisible(obj.getImage());
    }
  }
}
