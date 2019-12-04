package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

class DisplayHandler {
  private Button[] buttons;
  private TextView out;
  private TextView keyText;
  private TextView health;
  private TextView score;
  private TextView potions;
  private Button keyButton;

  DisplayHandler(
      Button[] b,
      TextView o,
      TextView k,
      Button keyButton,
      TextView hp,
      TextView pots,
      TextView score) {
    buttons = b;
    out = o;
    keyText = k;
    this.keyButton = keyButton;
    health = hp;
    potions = pots;
    this.score = score;
  }

  @SuppressLint("SetTextI18n")
  void updateHealth(int i) {
    health.setText("Health: " + i);
  }

  @SuppressLint("SetTextI18n")
  void updatePotions(int i) {
    potions.setText("Potions: " + i);
  }

  @SuppressLint("SetTextI18n")
  void updateScore(int i) {
    score.setText("Score: " + i);
  }

  private void hideKeyText() {
    keyText.setVisibility(INVISIBLE);
  }

  void showKeyText() {
    keyText.setVisibility(VISIBLE);
  }

  void showKeyButton() {
    keyButton.setVisibility(VISIBLE);
  }

  private void hideKeyButton() {
    keyButton.setVisibility(INVISIBLE);
  }

  void enableKeyButton() {
    keyButton.setEnabled(true);
    keyButton.setClickable(true);
  }

  private void disableKeyButton() {
    keyButton.setEnabled(false);
    keyButton.setClickable(false);
  }

  /** The initialization before displaying a sequence */
  @SuppressLint("SetTextI18n")
  void startSequence() {
    disableButtons(); // disable the buttons while the sequence is displaying
    disableKeyButton();
    hideKeyButton();
    hideKeyText();
    setButtonsVisible();
    out.setText("Wait for the sequence to display");
    out.setVisibility(VISIBLE);
  }

  /** The tasks to be executed after displaying a sequence. */
  @SuppressLint("SetTextI18n")
  void endSequence() {
    out.setText("Start!");
    out.setVisibility(VISIBLE);
    enableButtons(); // enable buttons after sequence is displayed
  }

  void setButtonInvisible(int i) {
    buttons[i].setVisibility(INVISIBLE);
  }

  /** Set all Button.Enabled and Clickable properties to true */
  private void enableButtons() {
    for (Button button : buttons) {
      button.setEnabled(true);
      button.setClickable(true);
    }
  }

  void disableButtons() {
    for (Button button : buttons) {
      button.setEnabled(false);
      button.setClickable(false);
    }
  }

  /** Make all sequence buttons visible */
  void setButtonsVisible() {
    for (Button button : buttons) {
      button.setVisibility(VISIBLE);
    }
  }

  void setText(String s) {
    out.setText(s);
    out.setVisibility(VISIBLE);
  }

  Button getButton(int i) {
    return buttons[i];
  }
}
