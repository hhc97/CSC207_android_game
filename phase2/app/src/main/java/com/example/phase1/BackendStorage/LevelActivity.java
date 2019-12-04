package com.example.phase1.BackendStorage;

import android.os.Bundle;
/**
 * This class is an abstract superclass of Level1Activity and Level2Activity.
 *
 * @author Pao Hua Lin
 */
public abstract class LevelActivity extends GameManager {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  /** Do when the left button is pressed */
  public void leftAction() {}

  /** Do when the right button is pressed */
  public void rightAction() {}

  /** Do when the jump button is pressed */
  public void jumpAction() {}

  /** Do when the attack button is pressed */
  public void attackAction() {}
}
