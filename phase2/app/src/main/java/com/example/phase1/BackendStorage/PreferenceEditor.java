package com.example.phase1.BackendStorage;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import com.example.phase1.R;

/**
 * An activity that has buttons to allow users to customize their preferences.
 *
 * @author Haocheng Hu
 */
public class PreferenceEditor extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
            .setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_preference_editor);
    currPlayer = getIntent().getIntExtra(sendPlayer, 0);
    updatePlayerInfo();
    restoreState();
  }

  /** Updates the textView in the activity to show current preferences. */
  private void updatePlayerInfo() {

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
  }

  // setters for each button
  public void clickDaySwitch(View view) {
    if (getDayOrNight() == 0) {
      findViewById(R.id.day).setVisibility(View.VISIBLE);
      findViewById(R.id.night).setVisibility(View.INVISIBLE);
      setDayOrNight(1);
    } else {
      findViewById(R.id.day).setVisibility(View.INVISIBLE);
      findViewById(R.id.night).setVisibility(View.VISIBLE);
      setDayOrNight(0);
    }
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

  /**
   * Exits the activity when the finish button is clicked.
   *
   * @param view The finish button.
   */
  public void clickFinish(View view) {
    finish();
  }

  /** Restores to the previous state of switches and backgrounds based on users old preference */
  public void restoreState() {
    Switch daynightswitch= findViewById(R.id.daynight);
    if (getDayOrNight() == 1) {
      daynightswitch.setChecked(true);
      findViewById(R.id.day).setVisibility(View.VISIBLE);
      findViewById(R.id.night).setVisibility(View.INVISIBLE);
      }
  }

}
