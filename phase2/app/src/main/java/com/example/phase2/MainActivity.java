package com.example.phase2;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.BackendStorage.SaveMenu;
import com.example.phase1.R;

/** The main activity that starts the game and loads into the save menu. */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
            .setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);


    // Move the two copies of the front background image, continuously.

    final ImageView backgroundOne = findViewById(R.id.grass);
    final ImageView backgroundTwo = findViewById(R.id.grass1);
    final ImageView backgroundThree = findViewById(R.id.vegetation);
    final ImageView backgroundFour = findViewById(R.id.vegetation2);
    ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setInterpolator(new LinearInterpolator());
    animator.setDuration(10000L);
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
              }
            });
animator.start();
  }


  /**
   * Load into the save menu when the start game button is clicked.
   *
   * @param view The start game button.
   */
  public void startGame(View view) {
    Intent intent = new Intent(this, SaveMenu.class);
    startActivity(intent);
  }
}
