package com.example.phase1;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifImageView;

public class Level1Activity extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    currPlayer = intent.getIntExtra("com.example.phase1.SEND_PLAYER", 0);
    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    if (getDayOrNight() == 0) {
      setContentView(R.layout.n_activity_level1);
    } else if (getDayOrNight() == 1) {
      setContentView(R.layout.activity_level1);
    }
    final ImageView backgroundOne = findViewById(R.id.grass);
    final ImageView backgroundTwo = findViewById(R.id.grass1);
    final ImageView backgroundThree = findViewById(R.id.vegetation);
    final ImageView backgroundFour = findViewById(R.id.vegetation2);
    final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    final pl.droidsonroids.gif.GifImageView hero = findViewById(R.id.hero);
    final GifImageView coin0 = findViewById(R.id.c1);
    final GifImageView coin1 = findViewById(R.id.c2);
    final GifImageView coin2 = findViewById(R.id.c3);
    final GifImageView enemy = findViewById(R.id.enemy);

    if (getCharacter() == 0) {
      hero.setImageResource(R.drawable.hero1);
    } else if (getCharacter() == 1) {
      hero.setImageResource(R.drawable.knight);
    }

    final Level1Manager manager = new Level1Manager();
    animator.setRepeatCount(1);
    animator.setInterpolator(new LinearInterpolator());
    animator.setDuration(17000L);

    enemy.setX(manager.Objects.get(0).getX());
    coin0.setX(manager.Objects.get(1).getX());
    coin1.setX(manager.Objects.get(2).getX());
    coin2.setX(manager.Objects.get(3).getX());

//    int[] location = new int[2];
//    enemy.getLocationOnScreen(location);
//    manager.Objects.get(0).setX(location[0]);
//    coin0.getLocationOnScreen(location);
//    manager.Objects.get(1).setX(location[0]);
//    coin1.getLocationOnScreen(location);
//    manager.Objects.get(2).setX(location[0]);
//    coin2.getLocationOnScreen(location);
//    manager.Objects.get(3).setX(location[0]);
//    System.out.println(location);

    // Move Graphic Components Right if Left Button is Pressed
    Button left = findViewById(R.id.left);
    //        left.setOnClickListener(
    //                new View.OnClickListener() {
    //                    @Override
    //                    public void onClick(View view) {
    //
    //                        animator.addUpdateListener(
    //                                new ValueAnimator.AnimatorUpdateListener() {
    //                                    @Override
    //                                    public void onAnimationUpdate(ValueAnimator animation) {
    //                                        final float progress = (float)
    // animation.getAnimatedValue();
    //                                        final float width1 = backgroundOne.getWidth();
    //                                        final float translationX1 = width1 * progress;
    //                                        final float width2 = backgroundThree.getWidth();
    //                                        final float translationX2 = width2 * progress - 10;
    //                                        backgroundOne.setTranslationX(translationX1);
    //                                        backgroundTwo.setTranslationX(translationX1 - width1);
    //                                        backgroundThree.setTranslationX(translationX2);
    //                                        backgroundFour.setTranslationX(translationX2 -
    // width2);
    //                                    }
    //                                });
    //                        animator.start();
    //                    }
    //                });

    left.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                hero.setX(manager.heroMoveLeft());
                hero.setScaleX(-1f);
                if (getCharacter() == 0) {
                  hero.setImageResource(R.drawable.walk1);
                } else if (getCharacter() == 1) {
                  hero.setImageResource(R.drawable.walk);
                }
                break;
              case MotionEvent.ACTION_UP:
                manager.update();
                enemy.setX(manager.Objects.get(0).getX());
                coin0.setX(manager.Objects.get(1).getX());
                coin1.setX(manager.Objects.get(2).getX());
                coin2.setX(manager.Objects.get(3).getX());
                if (!manager.Objects.get(0).getStates()){
                  enemy.setVisibility(View.INVISIBLE);}
                if (!manager.Objects.get(1).getStates()){
                  coin0.setVisibility(View.INVISIBLE);}
                if (!manager.Objects.get(2).getStates()){
                  coin1.setVisibility(View.INVISIBLE);}
                if (!manager.Objects.get(3).getStates()){
                  coin2.setVisibility(View.INVISIBLE);}
                break;
            }
            // ... Respond to touch events
            return true;
          }
        });

    // Move Graphic Components Left if Right Button is Pressed
    Button right = findViewById(R.id.right);
    //        right.setOnClickListener(
    //                new View.OnClickListener() {
    //                    @Override
    //                    public void onClick(View view) {
    //
    //                        animator.addUpdateListener(
    //                                new ValueAnimator.AnimatorUpdateListener() {
    //                                    @Override
    //                                    public void onAnimationUpdate(ValueAnimator animation) {
    //                                        final float progress = (float)
    // animation.getAnimatedValue();
    //                                        final float width1 = backgroundOne.getWidth();
    //                                        final float translationX1 = width1 * progress;
    //                                        final float width2 = backgroundThree.getWidth();
    //                                        final float translationX2 = width2 * progress - 10;
    //                                        backgroundOne.setTranslationX(-translationX1);
    //                                        backgroundTwo.setTranslationX(-translationX1 +
    // width1);
    //                                        backgroundThree.setTranslationX(-translationX2);
    //                                        backgroundFour.setTranslationX(-translationX2 +
    // width2);
    //                                        hero.setImageResource(R.drawable.walk);
    //                                    }
    //                                });
    //                        animator.start();
    //                    }
    //                });

    right.setOnTouchListener(
        new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            switch (action) {
              case MotionEvent.ACTION_DOWN:
                hero.setScaleX(1f);
                hero.setX(manager.heroMoveRight());
                if (getCharacter() == 0) {
                  hero.setImageResource(R.drawable.walk1);
                } else if (getCharacter() == 1) {
                  hero.setImageResource(R.drawable.walk);
                }
              case MotionEvent.ACTION_UP:
                manager.update();
                enemy.setX(manager.Objects.get(0).getX());
                coin0.setX(manager.Objects.get(1).getX());
                coin1.setX(manager.Objects.get(2).getX());
                coin2.setX(manager.Objects.get(3).getX());
                if (!manager.Objects.get(0).getStates()){
                  enemy.setVisibility(View.INVISIBLE);
                }
                if (!manager.Objects.get(1).getStates()){
                  coin0.setVisibility(View.INVISIBLE);}
                if (!manager.Objects.get(2).getStates()){
                  coin1.setVisibility(View.INVISIBLE);}
                if (!manager.Objects.get(3).getStates()){
                  coin2.setVisibility(View.INVISIBLE);}

                break;
            }
            // ... Respond to touch events
            return true;
          }
        });

    Button attack = findViewById(R.id.attack);
    attack.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            manager.player.attack();
            if (getCharacter() == 0) {
              hero.setImageResource(R.drawable.attack1);
            } else if (getCharacter() == 1) {
              hero.setImageResource(R.drawable.attack);
            }
            manager.update();
            enemy.setX(manager.Objects.get(0).getX());
            coin0.setX(manager.Objects.get(1).getX());
            coin1.setX(manager.Objects.get(2).getX());
            coin2.setX(manager.Objects.get(3).getX());
            if (!manager.Objects.get(0).getStates()){
              enemy.setVisibility(View.INVISIBLE);            }
            manager.player.notAttack();
          }
        });

    Button jump = findViewById(R.id.jump);
  }
}
