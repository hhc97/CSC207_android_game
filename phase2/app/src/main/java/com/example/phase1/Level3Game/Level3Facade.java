package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;
import android.widget.Button;

import java.util.ArrayList;

class Level3Facade {
  private Level3Manager Level3;

  @SuppressLint("StaticFieldLeak")
  private DisplayHandler displayHandler;

  void setAttempts(int i) { Level3.setAttempts(i); }

  void setButtonVisible(int i) {
    displayHandler.setButtonVisible(i);
  }

  void setButtonInvisible(int i) {
        displayHandler.setButtonInvisible(i);
  }

  void setDisplayHandler(DisplayHandler displayHandler) {
    this.displayHandler = displayHandler;
  }

  int getAttempts() {
    return Level3.getAttempts();
  }

  void setLevel3(Level3Manager level3) {
    this.Level3 = level3;
  }

  void startSequence() {
    displayHandler.startSequence();
  }

  void endSequence() { displayHandler.endSequence(); }

  void disableButtons() {
    displayHandler.disableButtons();
  }

  void setButtonsVisible(){displayHandler.setButtonsVisible();}

  void setButtonsInvisible(){displayHandler.setButtonsInvisible();}

  void enableButtons(){displayHandler.enableButtons();}

  void setText(String s) {
    displayHandler.setText(s);
  }

  ArrayList<Integer> getSequence() {
    return Level3.getSequence();
  }

  void setUserInput(int pressed) {
    Level3.setUserInput(pressed);
  }

  void clearInput() {
    Level3.clearInput();
  }

  int checkConditions() {
    return Level3.checkConditions();
  }

  Button getButton(int i){return displayHandler.getButton(i);}
}