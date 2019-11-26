package com.example.phase1.BackendStorage;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.phase1.R;

/**
 * An activity that has buttons to allow users to customize their preferences.
 *
 * @author Haocheng Hu, Nayan Saxena
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
    restoreState();
  }

  /**
   * Toggles and sets the background between Day and Night when the button is clicked.
   *
   * @param view The button that is clicked.
   */
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

  /**
   * Toggles and sets the character between Knight and Rogue when the button is clicked.
   *
   * @param view The button that is clicked.
   */
  public void clickCharSwitch(View view) {
    ImageView hero = findViewById(R.id.hero);
    TextView charText = findViewById(R.id.cname);

    if (getCharacter() == 0) {
      hero.setImageResource(R.drawable.run1);
      charText.setText(R.string.set_knight);
      setCharacter(1);
    } else {
      hero.setImageResource(R.drawable.run2);
      charText.setText(R.string.set_rogue);
      setCharacter(0);
    }
  }

  // setters for difficulty
  public void clickEasy(View view) {
    setDifficulty(0);
  }

  public void clickNormal(View view) {
    setDifficulty(1);
  }

  public void clickHard(View view) {
    setDifficulty(2);
  }

  /**
   * Exits the activity when the finish button is clicked.
   *
   * @param view The finish button.
   */
  public void clickFinish(View view) {
    finish();
  }

  /** Restores the state of the switches and backgrounds based on current user preferences. */
  private void restoreState() {
    Switch dayNightSwitch = findViewById(R.id.daynight);
    RadioButton easy = findViewById(R.id.easy);
    RadioButton normal = findViewById(R.id.normal);
    RadioButton hard = findViewById(R.id.hard);
    ImageView hero = findViewById(R.id.hero);
    TextView charText = findViewById(R.id.cname);
    Switch cSwitch = findViewById(R.id.cswitch);

    if (getDayOrNight() == 1) {
      dayNightSwitch.setChecked(true);
      findViewById(R.id.day).setVisibility(View.VISIBLE);
      findViewById(R.id.night).setVisibility(View.INVISIBLE);
    }

    if (getDifficulty() == 0) {
      easy.setChecked(true);
    } else if (getDifficulty() == 1) {
      normal.setChecked(true);
    } else {
      hard.setChecked(true);
    }

    if (getCharacter() == 1) {
      cSwitch.setChecked(true);
    } else {
      hero.setImageResource(R.drawable.run2);
      charText.setText(R.string.set_rogue);
    }
  }
}
