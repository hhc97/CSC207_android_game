package com.example.phase1.BackendStorage;

import android.os.Bundle;

public abstract class LevelActivity extends GameManager {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public void leftAction() {};

  public void rightAction() {};

  public void jumpAction() {};

  public void attackAction() {};
}
