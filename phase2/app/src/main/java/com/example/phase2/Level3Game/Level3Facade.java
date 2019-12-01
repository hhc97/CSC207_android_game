package com.example.phase2.Level3Game;

import android.annotation.SuppressLint;

import java.util.ArrayList;

class Level3Facade {
  private Level3Manager Level3;

  @SuppressLint("StaticFieldLeak")
  private static DisplayHandler displayHandler;

  static void setAttempts(int i) { Level3Manager.setAttempts(i); }

  static void setButtonVisible(int i) {
    displayHandler.setButtonVisible(i);
  }
    static void setButtonInvisible(int i) {
        displayHandler.setButtonInvisible(i);
    }

  void setDisplayHandler(DisplayHandler displayHandler) {
    Level3Facade.displayHandler = displayHandler;
  }

  static int getAttempts() {
    return Level3Manager.attempts;
  }

  void setLevel3(Level3Manager level3) {
    this.Level3 = level3;
  }

  static void startSequence() {
    displayHandler.startSequence();
  }

  static void endSequence() { displayHandler.endSequence(); }

  static void disableButtons() {
    DisplayHandler.disableButtons();
  }

  static void setButtonsVisible(){DisplayHandler.setButtonsVisible();}

  static void setButtonsInvisible(){DisplayHandler.setButtonsInvisible();}

  static void enableButtons(){DisplayHandler.enableButtons();}

  static void setText(String s) {
    DisplayHandler.setText(s);
  }

  static ArrayList<Integer> getSequence() {
    return Level3Manager.getSequence();
  }

  static void setUserInput(int pressed) {
    Level3Manager.setUserInput(pressed);
  }

  static void clearInput() {
    Level3Manager.clearInput();
  }

  static int checkConditions() {
    return Level3Manager.checkConditions();
  }
}
