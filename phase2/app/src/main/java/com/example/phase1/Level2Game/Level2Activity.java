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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.R;

import java.util.Timer;
import java.util.TimerTask;

public class Level2Activity extends GameManager {
  Level2Manager level2Manager = new Level2Manager();
  private Handler handler = new Handler();
  private Timer timer = new Timer();
  private TextView scoreLabel;
  private TextView healthLabel;
  private TextView message;

  private RelativeLayout screen;

  ValueAnimator animator;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setCurrPlayer(getIntent().getIntExtra(sendPlayer, 0));
    level2Manager.setParent(this);

    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    // Change the background theme to either day or night based on the user's choice.
    if (getDayOrNight() == 0) {
      setContentView(R.layout.n_activity_level2);
      screen = findViewById(R.id.n_2_layout);
    } else if (getDayOrNight() == 1) {
      setContentView(R.layout.activity_level2);
      screen = findViewById(R.id.lvl_2_layout);
    }

    // Change the hero's appearance based on the user's choice.
    final pl.droidsonroids.gif.GifImageView hero = findViewById(R.id.hero);
    if (getCharacter() == 0) {
      hero.setImageResource(R.drawable.run2);
    } else if (getCharacter() == 1) {
      hero.setImageResource(R.drawable.run1);
    }

    // Change health depending on the chosen difficulty level.
    if (getDifficulty() == 0) {
      addHealth(3);
    } else if (getDifficulty() == 1) {
      addHealth(2);
    } else if (getDifficulty() == 2) {
      addHealth(1);
    }

    // Text of the score and health.
    scoreLabel = findViewById(R.id.score);
    healthLabel = findViewById(R.id.health);

    message = findViewById(R.id.endtext);

    screen.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            tapStart();
          }
        });
  }

  // Runs the game.
  public void gameRun() {
    backgroundSetup();
    animator.start();
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            handler.post(
                new Runnable() {
                  @Override
                  public void run() {
                    // Update coordinates of the moving obstacles, as well as the score.
                    level2Manager.update();
                    scoreLabel.setText("Score: " + getScore());
                    healthLabel.setText("Health: " + getHealth());

                    // The level is over, so pause the auto-update to check collision and
                    // animation.
                    if (getHealth() <= 0) {

                      // Make the end-game text visible
                      findViewById(R.id.endtext).setVisibility(View.VISIBLE);
                      findViewById(R.id.endtext1).setVisibility(View.VISIBLE);
                      findViewById(R.id.endtext2).setVisibility(View.VISIBLE);
                      findViewById(R.id.menu).setVisibility(View.VISIBLE);
                      findViewById(R.id.potionbutton).setVisibility(View.VISIBLE);
                      message.setVisibility(View.VISIBLE);

                      // End the animation
                      animator.cancel();
                      timer.cancel();
                      //                      levelEndDelay();
                    }
                    else if (getScore() >= 2000) {
                      collectPhase();
                    }
                  }
                });
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

  // Visually show that the hero is jumping.
  public void tapScreen(View view) {
    preventTwoClick(view);
    final ImageView heroCharacter = findViewById(R.id.hero);
    final ObjectAnimator animationUp =
        ObjectAnimator.ofFloat(
            heroCharacter, "translationY", 0f, -100, -200f, -250f, -200f, -100f, 0f);
    animationUp.setDuration(1200L);

    // Add a listener that checks if the hero is jumping, and tells the Level2Manager.
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
    message.setVisibility(View.INVISIBLE);
    message.setText(getResources().getString(R.string.lose_message));
  }

  // Restarts the level if the player has clicked the button, but only if they have health potions.
  public void restartLevel(View view) {
    if (getPotion() > 0) {
      message.setVisibility(View.INVISIBLE);
      setHealth(100);
      gameRun();
    }
  }

  // Switches to the ending phase of Level 2.
  public void collectPhase() {}

  //  // A 3 second delay before starting Level 3.
  //  private void levelEndDelay() {
  //    handler.postDelayed(
  //        new Runnable() {
  //          @Override
  //          public void run() {
  //            startNextLevel();
  //            finish();
  //          }
  //        },
  //        3000);
  //  }
}
