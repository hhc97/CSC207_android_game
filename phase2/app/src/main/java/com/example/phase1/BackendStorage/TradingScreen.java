package com.example.phase1.BackendStorage;

import android.os.Bundle;
import android.view.View;

import com.example.phase1.R;

/**
 * An activity that allows players to trade their coins for items after they finish their first run
 * of the game.
 */
public class TradingScreen extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trading_screen);
    setCurrPlayer(getIntent().getIntExtra(sendPlayer, 0));
  }

  public void buyPotion(View view) {
    int potionPrice = 3;
    if (getCoin() >= potionPrice) {
      deductCoin(potionPrice);
      addPotion();
    }
  }

  public void buyKey(View view) {
    int keyPrice = 3;
    if (getCoin() >= keyPrice) {
      deductCoin(keyPrice);
      addKey();
    }
  }
}
