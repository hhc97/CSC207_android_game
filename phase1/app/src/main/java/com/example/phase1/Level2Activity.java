package com.example.phase1;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class Level2Activity extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(R.layout.activity_level2);

    final ImageView backgroundOne = findViewById(R.id.grass);
    final ImageView backgroundTwo = findViewById(R.id.mountains);

    final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setInterpolator(new LinearInterpolator());
    animator.setDuration(10000L);
    animator.addUpdateListener(
        new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            final float progress = (float) animation.getAnimatedValue();
            final float width = backgroundOne.getWidth();
            final float translationX = width * progress;
            backgroundOne.setTranslationX(-translationX);
            backgroundTwo.setTranslationX(-translationX + width);
          }
        });
    animator.start();
  }
}
