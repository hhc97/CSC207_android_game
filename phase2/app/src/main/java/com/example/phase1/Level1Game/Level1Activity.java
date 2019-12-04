package com.example.phase1.Level1Game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.phase1.BackendStorage.LevelActivity;
import com.example.phase1.Controls.ControlSchemeOne;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Monster;
import com.example.phase1.R;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

/** */
public class Level1Activity extends LevelActivity {

  private Button toMenuButton;
  private Button usePotionButton;
  private Level1Manager manager;
  private GifImageView hero;
  private GifImageView coin0;
  private GifImageView coin1;
  private GifImageView coin2;
  private GifImageView enemy0;
  private GifImageView enemy1;
  private GifImageView enemy2;
  private GifImageView healthPotion;
  private int activityLevel; // Day or Night background.
  private int[] heroAction = new int[4]; // Animations for hero, stand, walk, hurt, attack and die.
  private int[] enemyAction = new int[4]; // Animations for enemy, stand, walk, hurt and attack.
  private TextView scoreLabel;
  private TextView healthLabel;
  private TextView potionLabel;
  private TextView gameOverLabel;
  private TextView returnToMenuText;
  private TextView usePotionText;
  private int difficulty = 0; // default difficulty
  private int dayOrNight = 0; // default difficulty
  private ArrayList<GameObject> Objects;
  private Timer timer;
  private Handler handler = new Handler();
  private boolean isRunning = true;
  private ControlSchemeOne controlScheme;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCurrPlayer(getIntent().getIntExtra(sendPlayer, 0));
    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.controlScheme = new ControlSchemeOne(this);

    // calling initial setup methods.
    setup();
    nullAction();
    setHeroStatus();
    setLeftButton();
    setRightButton();
    setAttackButton();
    setJumpButton();
    setUsePotionButton();
    setReturnToMenuButton();
    startGame();
  }

  private void startGame() {
    isRunning = true;
    timer = new Timer();
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            if (isRunning) {
              handler.post(
                  new Runnable() {
                    @Override
                    public void run() {
                      gameLoopAction();
                    }
                  });
            }
          }
        },
        5,
        100);
  }

  // Set health and coin statistics for the hero.
  private void setHeroStatus() {
    manager.getPlayer().setHealth(getHealth());
    manager.getPlayer().setCoins(getCoin());
    manager.getPlayer().setScore(getScore());
    manager.getPlayer().setPotion(getPotion());
  }

  // Update states in Game Manager Class.
  private void updateStatesToGameManager() {
    setHealth(manager.getPlayer().getHealth());
    setCoin(manager.getPlayer().getCoins());
    setScore(manager.getPlayer().getScore());
    setPotion(manager.getPlayer().getPotion());
  }

  // Create and set event listener for left button.
  private void setLeftButton() {
    controlScheme.setLeftButton();
  }

  // Create and set event listener for right button
  private void setRightButton() {
    controlScheme.setRightButton();
  }
  // Create and set event listener for attack button
  private void setAttackButton() {
    controlScheme.setAttackButton();
  }

  @SuppressLint("ClickableViewAccessibility")
  private void setUsePotionButton() {
    usePotionButton = findViewById(R.id.potionbutton);
    usePotionButton.setVisibility(View.INVISIBLE);
    usePotionButton.setClickable(false);
    usePotionButton.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                usePotionAction();
              case MotionEvent.ACTION_UP:
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  @SuppressLint("ClickableViewAccessibility")
  private void setReturnToMenuButton() {
    toMenuButton = findViewById(R.id.menu);
    toMenuButton.setVisibility(View.INVISIBLE);
    toMenuButton.setClickable(false);
    toMenuButton.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                returnToMenuAction();
              case MotionEvent.ACTION_UP:
            }
            // ... Respond to touch events
            return true;
          }
        });
  }

  private void setJumpButton() {}

  // Reference to all front end objects from back
  private void setup() {
    this.difficulty = getDifficulty();
    this.dayOrNight = getDayOrNight();
    manager = new Level1Manager(this.difficulty);
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
    healthPotion = findViewById(R.id.potion);
    Objects.get(7).setImage(healthPotion);
    scoreLabel = (TextView) findViewById(R.id.score2);
    healthLabel = (TextView) findViewById(R.id.health2);
    potionLabel = (TextView) findViewById(R.id.potions);
    gameOverLabel = (TextView) findViewById(R.id.endtext);
    returnToMenuText = (TextView) findViewById(R.id.endtext3);
    usePotionText = (TextView) findViewById(R.id.endtext2);
    setHeroStatus();
    setHeroAction();
    setEnemyAction();
    setAllObjectsToVisible();
    updateImage();
    nullAction();
    heroStandAnimation();
  }

  private void checkIsWinning() {
    if (manager.isWinning() && isRunning) {
      isRunning = false;
      if (manager.hasGotTheEasterEgg()) {
        startSpecifiedLevel(3);
      } else {
        startNextLevel();
      }
      finish();
    }
  }

  private void checkIsLosing() {
    if (!manager.isPlayerAlive()) {
      gameOver();
    }
  }

  private void gameOver() {
    isRunning = false;
    timer.cancel(); // cancel timer to prevent crashing
    imageInvisible(manager.getPlayer().getImage());
    showEndText();
  }

  private void showEndText() {
    gameOverLabel.setVisibility(View.VISIBLE);
    returnToMenuText.setVisibility(View.VISIBLE);
    usePotionText.setVisibility(View.VISIBLE);
    returnToMenuText.setVisibility(View.VISIBLE);
    toMenuButton.setVisibility(View.VISIBLE);
    toMenuButton.setClickable(true);
    usePotionText.setVisibility(View.VISIBLE);
    usePotionButton.setVisibility(View.VISIBLE);
    usePotionButton.setClickable(true);
  }

  /** sequences of method calls to resolve move right when right button is pressed */
  public void rightAction() {
    manager.rightButtonPress();
    heroFacingRight();
    heroWalkAnimation();
  }

  /** sequences of method calls to resolve move left when left button is pressed */
  public void leftAction() {
    manager.leftButtonPress();
    heroFacingLeft();
    heroWalkAnimation();
  }

  /** sequences of method calls to resolve attack when attack button is pressed */
  public void attackAction() {
    manager.attackButtonPress();
    nullAction();
    heroAttackAnimation();
    for (int i = 1; i <= 3; i++) {
      if (((Monster) Objects.get(i)).isGetHit()) enemyHurtAnimation(i);
    }
  }

  private void usePotionAction() {
    if (getPotion() > 0) { // if there are any potions left
      usePotionText.setVisibility(View.INVISIBLE);
      usePotionButton.setVisibility(View.INVISIBLE);
      usePotionButton.setClickable(false);
      returnToMenuText.setVisibility(View.INVISIBLE);
      toMenuButton.setVisibility(View.INVISIBLE);
      toMenuButton.setClickable(false);
      gameOverLabel.setVisibility(View.INVISIBLE);
      manager.usePotionButtonPress();
      imageVisible(manager.getPlayer().getImage());
      updateStatesToGameManager(); // reset the game to before it started
      startGame();
    }
  }

  private void gameLoopAction() {
    manager.update();
    nullAction();
    updateStatesToGameManager();
    updateImage();
    checkIsWinning();
    checkIsLosing();
  }

  private void returnToMenuAction() {
    finish();
  }

  /** resolve jump button being pressed, not currently used. */
  public void jumpAction() {}

  private void nullAction() { // animation updates when nothing is pressed

    for (int i = 1; i <= 3; i++) { // for each one of the 3 monsters objects
      if (((Monster) Objects.get(i)).isMoveLeft()) {
        enemyFacingLeft(i);
      } else {
        enemyFacingRight(i);
      }

      if (((Monster) Objects.get(i)).isAttack() && Objects.get(i).getStates()) {
        // if the monster is attacking and is still alive
        enemyAttackAnimation(i);
        heroHurtAnimation();
      } else {
        enemyWalkAnimation(i);
      }
    }
  }

  private void updateImage() {
    scoreLabel.setText("Score: " + manager.getPlayer().getScore());
    healthLabel.setText("Health: " + manager.getPlayer().getHealth());
    potionLabel.setText("Potions:" + manager.getPlayer().getPotion());
    for (GameObject obj : Objects) {
      obj.getImage().setX(obj.getX());
    }
    for (int i = 1; i < Objects.size(); i++) {
      if (!Objects.get(i).getStates()) {
        imageInvisible(Objects.get(i).getImage());
      }
    }
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
