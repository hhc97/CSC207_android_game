package com.example.phase1.Level2Game;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
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
  private TextView levelStart;
  private TextView levelOver;

  // COMMENTED OUT. WE WILL BE USING THIS IN PHASE 2.
  //  // Sizes. Note that in landscape, width > height.
  //  private int screenWidth;
  //  private int screenHeight;
  //
  //  // Speed
  //  private int rockSpeed;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra(sendPlayer, 0));
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
    } else if (getDayOrNight() == 1) {
      setContentView(R.layout.activity_level2);
    }

    // Images in the layout for Level 2.
    final ImageView backgroundOne = findViewById(R.id.grass);
    final ImageView backgroundTwo = findViewById(R.id.grass1);
    final ImageView backgroundThree = findViewById(R.id.vegetation);
    final ImageView backgroundFour = findViewById(R.id.vegetation2);
    final ImageView backgroundFive = findViewById(R.id.tree);
    final ImageView backgroundSix = findViewById(R.id.rock1);
    final ImageView backgroundSeven = findViewById(R.id.tree2);
    final ImageView backgroundEight = findViewById(R.id.tree1_c);
    final ImageView backgroundNine = findViewById(R.id.rock1_c);
    final ImageView backgroundTen = findViewById(R.id.tree2_c);

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

    // Move the two copies of the front background image, continuously.
    final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
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
            backgroundFive.setTranslationX(-translationX1);
            backgroundSix.setTranslationX(-translationX1 + width1);
            backgroundSeven.setTranslationX(-translationX2);
            backgroundEight.setTranslationX(-translationX2 + width2);
            backgroundNine.setTranslationX(-translationX1);
            backgroundTen.setTranslationX(-translationX1 + width1);
          }
        });

    // Text with instructions before the game starts.
    levelStart = findViewById(R.id.Level2Start);

    // Text of the score and health.
    scoreLabel = findViewById(R.id.score);
    healthLabel = findViewById(R.id.health);

    // Text with message when game ends.
    levelOver = findViewById(R.id.end);

    handler.postDelayed(
        new Runnable() {
          @Override
          public void run() {
            // After 5s delay, remove the instructions text and start the animation.
            levelStart.setVisibility(View.INVISIBLE);
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
                            if (getHealth() == 0) {
                              levelOver.setVisibility(View.VISIBLE);
                              animator.cancel();
                              timer.cancel();
                              levelEndDelay();
                            }
                          }
                        });
                  }
                },
                0,
                20);
          }
        },
        5000);
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

  // A 3 second delay before starting Level 3.
  private void levelEndDelay() {
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        startNextLevel();
      }
    }, 3000);
  }
}
