package com.example.phase1.BackendStorage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.phase1.R;

public class PreferenceEditor extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preference_editor);
    currPlayer = getIntent().getIntExtra(sendPlayer, 0);
    TextView header = findViewById(R.id.textView3);
    String string = "Configure preferences for \"" + getName() + "\"";
    header.setText(string);
    updatePlayerInfo();
  }

  private void updatePlayerInfo() {
    TextView display = findViewById(R.id.textView4);
    if (currPlayer != -1 && !getName().equals("NAME")) {
      String character;
      if (getCharacter() == 0) {
        character = "Rogue";
      } else {
        character = "Knight";
      }

      String nightDay;
      if (getDayOrNight() == 0) {
        nightDay = "Night";
      } else {
        nightDay = "Day";
      }

      String difficulty;
      if (getDifficulty() == 0) {
        difficulty = "Easy";
      } else if (getDifficulty() == 1) {
        difficulty = "Normal";
      } else {
        difficulty = "Hard";
      }
      String stats =
          "Current preferences:"
              + "\nScene: "
              + nightDay
              + "\nDifficulty: "
              + difficulty
              + "\nCharacter: "
              + character;
      display.setText(stats);
    } else {
      display.setText(R.string.no_player);
    }
  }

  public void clickDay(View view) {
    setDayOrNight(1);
    updatePlayerInfo();
  }

  public void clickNight(View view) {
    setDayOrNight(0);
    updatePlayerInfo();
  }

  public void clickKnight(View view) {
    setCharacter(1);
    updatePlayerInfo();
  }

  public void clickRogue(View view) {
    setCharacter(0);
    updatePlayerInfo();
  }

  public void clickEasy(View view) {
    setDifficulty(0);
    updatePlayerInfo();
  }

  public void clickNormal(View view) {
    setDifficulty(1);
    updatePlayerInfo();
  }

  public void clickHard(View view) {
    setDifficulty(2);
    updatePlayerInfo();
  }

  public void clickFinish(View view) {
    finish();
  }
}
