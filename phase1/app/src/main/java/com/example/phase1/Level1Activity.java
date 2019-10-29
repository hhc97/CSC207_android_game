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

import com.example.phase1.BackendStorage.GameManager;

import pl.droidsonroids.gif.GifImageView;

public class Level1Activity extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra("com.example.phase1.SEND_PLAYER", 0));
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

    // Move Graphic Components Right if Left Button is Pressed
    Button left = findViewById(R.id.left);

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

                if (!((Monster) manager.Objects.get(0)).isMoveLeft()) {
                  enemy.setScaleX(1f);
                } else {
                  enemy.setScaleX(-1f);
                }

                if (((Monster) manager.Objects.get(0)).isAttack() && enemy.isShown()) {
                  enemy.setImageResource(R.drawable.e_attack);

                  if (getCharacter() == 0) {
                    hero.setImageResource(R.drawable.hurt1);
                  } else if (getCharacter() == 1) {
                    hero.setImageResource(R.drawable.hurt2);
                  }

                } else {
                  enemy.setImageResource(R.drawable.walke);
                  if (getCharacter() == 0) {
                    hero.setImageResource(R.drawable.walk1);
                  } else if (getCharacter() == 1) {
                    hero.setImageResource(R.drawable.walk);
                  }
                }

                break;
              case MotionEvent.ACTION_UP:
                manager.update();
                enemy.setX(manager.Objects.get(0).getX());
                coin0.setX(manager.Objects.get(1).getX());
                coin1.setX(manager.Objects.get(2).getX());
                coin2.setX(manager.Objects.get(3).getX());
                if (!manager.Objects.get(0).getStates()) {
                  enemy.setVisibility(View.INVISIBLE);
                }
                if (!manager.Objects.get(1).getStates()) {
                  coin0.setVisibility(View.INVISIBLE);
                }
                if (!manager.Objects.get(2).getStates()) {
                  coin1.setVisibility(View.INVISIBLE);
                }
                if (!manager.Objects.get(3).getStates()) {
                  coin2.setVisibility(View.INVISIBLE);
                }
                break;
            }
            // ... Respond to touch events
            return true;
          }
        });

    // Move Graphic Components Left if Right Button is Pressed
    Button right = findViewById(R.id.right);

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
                if (!manager.Objects.get(0).getStates()) {
                  enemy.setVisibility(View.INVISIBLE);
                }
                if (!manager.Objects.get(1).getStates()) {
                  coin0.setVisibility(View.INVISIBLE);
                }
                if (!manager.Objects.get(2).getStates()) {
                  coin1.setVisibility(View.INVISIBLE);
                }
                if (!manager.Objects.get(3).getStates()) {
                  coin2.setVisibility(View.INVISIBLE);
                }

                if (!((Monster) manager.Objects.get(0)).isMoveLeft()) {
                  enemy.setScaleX(1f);
                } else {
                  enemy.setScaleX(-1f);
                }

                if (((Monster) manager.Objects.get(0)).isAttack() && enemy.isShown()) {
                  enemy.setImageResource(R.drawable.e_attack);

                  if (getCharacter() == 0) {
                    hero.setImageResource(R.drawable.hurt1);
                  } else if (getCharacter() == 1) {
                    hero.setImageResource(R.drawable.hurt2);
                  }

                } else {
                  enemy.setImageResource(R.drawable.walke);
                  if (getCharacter() == 0) {
                    hero.setImageResource(R.drawable.walk1);
                  } else if (getCharacter() == 1) {
                    hero.setImageResource(R.drawable.walk);
                  }
                }
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
            if (!manager.Objects.get(0).getStates()) {
              enemy.setVisibility(View.INVISIBLE);
            }
            manager.player.notAttack();
          }
        });
  }
}
