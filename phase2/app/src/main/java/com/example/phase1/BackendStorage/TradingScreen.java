package com.example.phase1.BackendStorage;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phase1.R;

/**
 * An activity that allows players to trade their coins for items after they finish their first run
 * of the game.
 *
 * @author Haocheng Hu, Nayan Saxena
 */
public class TradingScreen extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_trading_screen);
    currPlayer = getIntent().getIntExtra(sendPlayer, 0);
    updateInventory();
    moveBackground();
  }

  /**
   * Buys a potion, if the buy potion button is clicked and the player has enough coins.
   *
   * @param view The buy potion button.
   */
  public void buyPotion(View view) {
    int coinPotionPrice = 3;
    int pointPotionPrice = coinPotionPrice * 500;
    if (getCoin() >= coinPotionPrice) {
      deductCoin(coinPotionPrice);
      addPotion();
    } else if (getScore() >= pointPotionPrice) {
      deductScore(pointPotionPrice);
      addPotion();
    }
    updateInventory();
  }
  /**
   * Buys a key, if the buy key button is clicked and the player has enough coins.
   *
   * @param view The buy key button.
   */
  public void buyKey(View view) {
    int coinKeyPrice = 3;
    int pointKeyPrice = coinKeyPrice * 500;
    if (getCoin() >= coinKeyPrice) {
      deductCoin(coinKeyPrice);
      addKey();
    } else if (getScore() >= pointKeyPrice) {
      deductScore(pointKeyPrice);
      addKey();
    }
    updateInventory();
  }

  /**
   * Updates the TextView to display the current inventory at the beginning, and also after every
   * transaction.
   */
  private void updateInventory() {
    TextView display = findViewById(R.id.textView2);
    String inventory =
        "Current inventory:\n"
            + "\nCoins: "
            + getCoin()
            + "\nPoints: "
            + getScore()
            + "\n\nPotions: "
            + getPotion()
            + "\nKeys: "
            + getBonusKeys();
    display.setText(inventory);
  }

  /**
   * After the user has finished trading and clicks finish, start the game again.
   *
   * @param view The finish button that is clicked.
   */
  public void clickFinish(View view) {
    setHealth(100);
    startSpecifiedLevel(1);
    finish();
  }

  /** Moves the background. */
  public void moveBackground() {
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
}
