package com.example.phase1.Level2Game;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.BackendStorage.LevelActivity;
import com.example.phase1.Controls.ControlSchemeOne;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.R;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;
import java.util.List;
import java.util.ArrayList;

public class Level2Activity extends LevelActivity {

  Level2Manager level2Manager = new Level2Manager();
  private Handler handler = new Handler();
  private Timer timer = new Timer();
  private ValueAnimator animator;
  private TextView scoreLabel;
  private TextView healthLabel;
  private TextView potionLabel;
  private Button rightButton;
  private List<GameObject> gameObjects;
  private boolean isRunning = true;
  private boolean playerMovable = false;
  //  private TextView message;
  private RelativeLayout screen;
  private ControlSchemeOne controlSchemeOne;

  // Images in the layout for Level 2.
  ImageView backgroundOne;
  ImageView backgroundTwo;
  ImageView backgroundThree;
  ImageView backgroundFour;
  ImageView oakTree;
  ImageView oakTreeCopy;
  ImageView pineTree;
  ImageView pineTreeCopy;
  ImageView rock;
  ImageView rockCopy;
  ImageView coin1;
  ImageView coin2;
  ImageView coin3;
  ImageView potion;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setCurrPlayer(getIntent().getIntExtra(sendPlayer, 0));
    level2Manager.setParent(this);

    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title bar.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    getPreferences();
    runIntro();

    this.gameObjects = level2Manager.getGameObjects();
    coin1 = findViewById(R.id.c);
    gameObjects.get(0).setImage((GifImageView) coin1);
    coin2 = findViewById(R.id.c1);
    gameObjects.get(1).setImage((GifImageView) coin2);
    coin3 = findViewById(R.id.c2);
    gameObjects.get(2).setImage((GifImageView) coin3);
    potion = findViewById(R.id.potion);
    gameObjects.get(3).setImage((GifImageView) potion);
    setHeroStates();

    // Text of the score and health.
    scoreLabel = findViewById(R.id.score);
    healthLabel = findViewById(R.id.health);
    potionLabel = findViewById(R.id.potions);

    updateLabels();

    //    message = findViewById(R.id.endtext);

    this.controlSchemeOne = new ControlSchemeOne(this);
    controlSchemeOne.setRightButton();
    controlSchemeOne.setLeftButton();

    GifImageView heroCharacter = findViewById(R.id.hero);
    level2Manager.getPlayer().setImage(heroCharacter);
    screen.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            tapStart();
          }
        });
  }

  private void gameRun() {
    isRunning = true;
    backgroundSetup();
    animator.start();
    findViewById(R.id.tut1).setVisibility(View.INVISIBLE);
    findViewById(R.id.tut2).setVisibility(View.INVISIBLE);
    findViewById(R.id.tut).setVisibility(View.INVISIBLE);
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
        0,
        20);
  }

  // Sets up all the information necessary for background animation.
  public void backgroundSetup() {

    backgroundOne = findViewById(R.id.grass);
    backgroundTwo = findViewById(R.id.grass1);
    backgroundThree = findViewById(R.id.vegetation);
    backgroundFour = findViewById(R.id.vegetation2);
    oakTree = findViewById(R.id.tree);
    rock = findViewById(R.id.rock1);
    pineTree = findViewById(R.id.tree2);
    oakTreeCopy = findViewById(R.id.tree1_c);
    rockCopy = findViewById(R.id.rock1_c);
    pineTreeCopy = findViewById(R.id.tree2_c);

    // Move the two copies of the front background image, continuously.
    animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setInterpolator(new LinearInterpolator());
    animator.setDuration(5000L);

    animator.addUpdateListener(
        new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            final float progress = (float) animation.getAnimatedValue();
            final float width1 = backgroundOne.getWidth();
            final float width2 = backgroundThree.getWidth();
            final float translationX1 = width1 * progress;
            final float translationX2 = width2 * progress - 10;
            backgroundOne.setTranslationX(-translationX1);
            backgroundTwo.setTranslationX(-translationX1 + width1);
            backgroundThree.setTranslationX(-translationX2);
            backgroundFour.setTranslationX(-translationX2 + width2);
            rock.setTranslationX(-translationX1);
            rockCopy.setTranslationX(-translationX1 + width1);
            oakTree.setTranslationX(-translationX2);
            oakTreeCopy.setTranslationX(-translationX2 + width2);
            pineTree.setTranslationX(-translationX1);
            pineTreeCopy.setTranslationX(-translationX1 + width1);
          }
        });
  }

  // Visually show that the hero is jumping, if jump button is pressed.
  public void tapScreen(View view) {
    preventTwoClick(view);
    final GifImageView heroCharacter = findViewById(R.id.hero);
    final ObjectAnimator animationUp =
        ObjectAnimator.ofFloat(
            heroCharacter, "translationY", 0f, -100, -200f, -250f, -200f, -100f, 0f);
    animationUp.setDuration(1200L);

    // Adds a listener that checks if the hero is currently jumping, and tells the Level2Manager.
    animationUp.addListener(
        new Animator.AnimatorListener() {
          @Override
          public void onAnimationStart(Animator animationUp) {
            level2Manager.playerJump(true);
          }

          @Override
          public void onAnimationEnd(Animator animationUp) {
            level2Manager.playerJump(false);
          }

          @Override
          public void onAnimationRepeat(Animator animationUp) {}

          @Override
          public void onAnimationCancel(Animator animationUp) {}
        });
    animationUp.start();
  }

  // Method to avoid double/spam tapping to jump.
  private void preventTwoClick(final View view) {
    view.setEnabled(false);
    view.postDelayed(
        new Runnable() {
          public void run() {
            view.setEnabled(true);
          }
        },
        500);
  }

  // Starts the actual "running" component for Level 2 if screen is clicked.
  public void tapStart() {
    gameRun();
    screen.setClickable(false);
    //    message.setVisibility(View.INVISIBLE);
    //    message.setText(getResources().getString(R.string.lose_message));
  }

  // Restarts the level if the player has clicked the button, but only if they have health potions.
  public void resumeLevel(View view) {
    if (getPotion() > 0) {
      //      message.setVisibility(View.INVISIBLE);
      setPotion(getPotion() - 1);
      setHealth(100);
      restartLevel();
      finish();
    }
  }

  public void returnMenu(View view) {
    finish();
  }

  private void updateLabels() {
    scoreLabel.setText("Score: " + getScore());
    healthLabel.setText("Health: " + (getHealth()));
    potionLabel.setText("Potions: " + getPotion());
  }

  // Switches to the ending phase of Level 2.
  public void collectPhase() {
    level2Manager.playerAlive();
    level2Manager.update();
    updateGameObjectsImage();
    updateStatesToGameManager();
    updateLabels();
    coin1.setVisibility(View.VISIBLE);
    coin2.setVisibility(View.VISIBLE);
    coin3.setVisibility(View.VISIBLE);
    potion.setVisibility(View.VISIBLE);
    rock.setVisibility(View.INVISIBLE);
    rockCopy.setVisibility(View.INVISIBLE);
    oakTree.setVisibility(View.INVISIBLE);
    oakTreeCopy.setVisibility(View.INVISIBLE);
    pineTree.setVisibility(View.INVISIBLE);
    pineTreeCopy.setVisibility(View.INVISIBLE);
    findViewById(R.id.endtext).setVisibility(View.INVISIBLE);
    findViewById(R.id.endtext1).setVisibility(View.INVISIBLE);
    findViewById(R.id.endtext2).setVisibility(View.INVISIBLE);
    findViewById(R.id.menu).setVisibility(View.INVISIBLE);
    findViewById(R.id.potionbutton).setVisibility(View.INVISIBLE);
    findViewById(R.id.endInstructions).setVisibility(View.VISIBLE);
  }

  private void updateGameObjectsImage() {
    for (GameObject obj : gameObjects) {
      obj.getImage().setX(obj.getX());
      if (!obj.getStates()) {
        imageInvisible(obj.getImage());
      }
    }
  }

  // Customizes level based on what the user chose.
  private void getPreferences() {
    // Changes background theme.
    if (getDayOrNight() == 0) {
      setContentView(R.layout.n_activity_level2);
      screen = findViewById(R.id.n_2_layout);
    } else if (getDayOrNight() == 1) {
      setContentView(R.layout.activity_level2);
      screen = findViewById(R.id.lvl_2_layout);
    }

    // Changes the hero's appearance.
    final pl.droidsonroids.gif.GifImageView hero = findViewById(R.id.hero);
    if (getCharacter() == 0) {
      hero.setImageResource(R.drawable.run2);
    } else if (getCharacter() == 1) {
      hero.setImageResource(R.drawable.run1);
    }
  }

  public void moveRight(View view) {
    rightAction();
  }

  public void rightAction() {
    if (playerMovable) {
      heroFacingRight();
      level2Manager.rightAction();
      (level2Manager.getPlayer().getImage()).setX(level2Manager.getPlayer().getX());
      level2Manager.update();
      updateGameObjectsImage();
      updateStatesToGameManager();
      updateLabels();
      checkIsWinning();
    }
  }

  public void leftAction() {
    if (playerMovable) {
      heroFacingLeft();
      level2Manager.leftAction();
      (level2Manager.getPlayer().getImage()).setX(level2Manager.getPlayer().getX());
      updateGameObjectsImage();
      level2Manager.update();
      updateGameObjectsImage();
      updateStatesToGameManager();
      updateLabels();
      checkIsWinning();
    }
  }

  private void imageInvisible(GifImageView image) {
    image.setVisibility(View.INVISIBLE);
  }

  // Update states in Game Manager Class.
  private void updateStatesToGameManager() {
    setCoin(level2Manager.getPlayer().getCoins());
    setPotion(level2Manager.getPlayer().getPotion());
  }

  private void gameLoopAction() {
    level2Manager.update();
    updateLabels();

    //    The level is over, so pause the auto-update to check collision and
    //    animation.
    if (getHealth() <= 0) {
      healthLabel.setText("Health: 0");

      // Make the end-game text visible
      findViewById(R.id.endtext).setVisibility(View.VISIBLE);
      findViewById(R.id.endtext1).setVisibility(View.VISIBLE);
      findViewById(R.id.endtext2).setVisibility(View.VISIBLE);
      findViewById(R.id.menu).setVisibility(View.VISIBLE);
      findViewById(R.id.potionbutton).setVisibility(View.VISIBLE);
      //                      message.setVisibility(View.VISIBLE);

      // End the animation
      animator.cancel();
      timer.cancel();

    } else if (getScore() >= 1500) {
      isRunning = false;
      playerMovable = true;
      collectPhase();

      // End the animation
      animator.cancel();
      timer.cancel();
    }
  }

  // Flip the hero sprite left.
  private void heroFacingLeft() {
    level2Manager.getPlayer().getImage().setScaleX(-1f);
  }

  // Flip the hero sprite right.
  private void heroFacingRight() {
    level2Manager.getPlayer().getImage().setScaleX(1f);
  }

  private void checkIsWinning() {
    if (level2Manager.checkIsWinning()) {
      levelEndDelay();
    }
  }
  // A 3 second delay before starting Level 3.
  private void levelEndDelay() {
    handler.postDelayed(
        new Runnable() {
          @Override
          public void run() {
            startNextLevel();
            finish();
          }
        },
        1000);
  }

  private void setHeroStates() {
    level2Manager.getPlayer().setStates(true);
    level2Manager.getPlayer().setHealth(getHealth());
    level2Manager.getPlayer().setScore(getScore());
    level2Manager.getPlayer().setPotion(getPotion());
  }

  public void runIntro() {
    findViewById(R.id.tut2).setVisibility(View.VISIBLE);
    Handler handler = new Handler();
    handler.postDelayed(
        new Runnable() {
          @Override
          public void run() {
            findViewById(R.id.tut1).setVisibility(View.VISIBLE);
          }
        },
        1600);

    handler.postDelayed(
        new Runnable() {
          @Override
          public void run() {
            findViewById(R.id.tut1).setVisibility(View.INVISIBLE);
            findViewById(R.id.tut).setVisibility(View.VISIBLE);
          }
        },
        5000);
  }
}
