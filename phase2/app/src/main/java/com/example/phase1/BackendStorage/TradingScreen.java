package com.example.phase1.BackendStorage;

import android.os.Bundle;

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
}
