package com.example.phase1;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class Level1Activity extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.n_activity_level1);

    final ImageView backgroundOne = findViewById(R.id.grass);
    final ImageView backgroundTwo = findViewById(R.id.grass1);
    final ImageView backgroundThree = findViewById(R.id.vegetation);
    final ImageView backgroundFour = findViewById(R.id.vegetation2);
    final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    final pl.droidsonroids.gif.GifImageView hero = findViewById(R.id.hero);
    animator.setRepeatCount(1);
    animator.setInterpolator(new LinearInterpolator());
    animator.setDuration(17000L);

    // Move Graphic Components Right if Left Button is Pressed
    Button left = findViewById(R.id.left);
    left.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                  @Override
                  public void onAnimationUpdate(ValueAnimator animation) {
                    final float progress = (float) animation.getAnimatedValue();
                    final float width1 = backgroundOne.getWidth();
                    final float translationX1 = width1 * progress;
                    final float width2 = backgroundThree.getWidth();
                    final float translationX2 = width2 * progress - 10;
                    backgroundOne.setTranslationX(translationX1);
                    backgroundTwo.setTranslationX(translationX1 - width1);
                    backgroundThree.setTranslationX(translationX2);
                    backgroundFour.setTranslationX(translationX2 - width2);
                  }
                });
            animator.start();
          }
        });

    // Move Graphic Components Left if Right Button is Pressed
    Button right = findViewById(R.id.right);
    right.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                  @Override
                  public void onAnimationUpdate(ValueAnimator animation) {
                    final float progress = (float) animation.getAnimatedValue();
                    final float width1 = backgroundOne.getWidth();
                    final float translationX1 = width1 * progress;
                    final float width2 = backgroundThree.getWidth();
                    final float translationX2 = width2 * progress - 10;
                    backgroundOne.setTranslationX(-translationX1);
                    backgroundTwo.setTranslationX(-translationX1 + width1);
                    backgroundThree.setTranslationX(-translationX2);
                    backgroundFour.setTranslationX(-translationX2 + width2);
                  }
                });
            animator.start();
          }
        });
    Button attack = findViewById(R.id.attack);
      attack.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      hero.setImageResource(R.drawable.attack);
                  }
              });

    Button jump = findViewById(R.id.jump);
  }
}
