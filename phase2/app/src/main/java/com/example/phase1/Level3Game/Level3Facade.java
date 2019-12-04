package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;
import android.widget.Button;

import java.util.ArrayList;

class Level3Facade {
  private Level3Manager level3;

  @SuppressLint("StaticFieldLeak")
  private DisplayHandler displayHandler;

  void setAttempts(int i) {
    level3.setAttempts(i);
  }

  void setButtonInvisible(int i) {
    displayHandler.setButtonInvisible(i);
  }

  void setDisplayHandler(DisplayHandler displayHandler) {
    this.displayHandler = displayHandler;
  }

  int getAttempts() {
    return level3.getAttempts();
  }

  void setLevel3(Level3Manager level3) {
    this.level3 = level3;
  }

  void startSequence() {
    displayHandler.startSequence();
  }

  void endSequence() {
    displayHandler.endSequence();
  }

  void disableButtons() {
    displayHandler.disableButtons();
  }

  void setButtonsVisible() {
    displayHandler.setButtonsVisible();
  }

  void setText(String s) {
    displayHandler.setText(s);
  }

  ArrayList<Integer> getSequence() {
    return level3.getSequence();
  }

  void setUserInput(int pressed) {
    level3.setUserInput(pressed);
  }

  void clearInput() {
    level3.clearInput();
  }

  int checkConditions() {
    return level3.checkConditions();
  }

  Button getButton(int i) {
    return displayHandler.getButton(i);
  }

  int getToComplete() {
    return level3.getToComplete();
  }

  void completeSequence() {
    level3.completeSequence();
  }

  int getLength() {
    return level3.getLength();
  }

  int getDifficulty() {
    return level3.getDifficulty();
  }

  void enableKeyButton() {
    displayHandler.enableKeyButton();
  }

  void disableKeyButton() {
    displayHandler.disableButtons();
  }

  void showKeyButton() {
    displayHandler.showKeyButton();
  }

  void showKeyText() {
    displayHandler.showKeyText();
  }

  void updateHealth(int i) {
    displayHandler.updateHealth(i);
  }

  void updatePotions(int i) {
    displayHandler.updatePotions(i);
  }

  void updateScore(int i) {
    displayHandler.updateScore(i);
  }
}
