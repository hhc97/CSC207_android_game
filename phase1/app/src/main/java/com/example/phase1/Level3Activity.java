package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;

import com.example.phase1.BackendStorage.GameManager;

public class Level3Activity extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra("com.example.phase1.SEND_PLAYER", 0));
    setContentView(R.layout.activity_level3);
  }
}
