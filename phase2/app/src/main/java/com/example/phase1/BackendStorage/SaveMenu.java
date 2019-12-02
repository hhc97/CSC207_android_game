package com.example.phase1.BackendStorage;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.phase1.R;

/**
 * The activity responsible for displaying the save menu, where users can choose a slot to save, and
 * also resume previous saves.
 *
 * @author Haocheng Hu
 */
public class SaveMenu extends GameManager {
  private Button[] buttons;
  static final String sendName = "com.example.phase1.BackendStorage.SEND_NAME";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.n_activity_save_menu);
    Button save1 = findViewById(R.id.button3);
    Button save2 = findViewById(R.id.button4);
    Button save3 = findViewById(R.id.button5);
    Button save4 = findViewById(R.id.button6);
    Button save5 = findViewById(R.id.button7);
    Button save6 = findViewById(R.id.button8);
    buttons = new Button[] {save1, save2, save3, save4, save5, save6};
    updateButtons();

    // Move the two copies of the front background image, continuously.
    final ImageView backgroundOne = findViewById(R.id.grass);
    final ImageView backgroundTwo = findViewById(R.id.grass1);
    final ImageView backgroundThree = findViewById(R.id.vegetation);
    final ImageView backgroundFour = findViewById(R.id.vegetation2);
    ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setInterpolator(new LinearInterpolator());
    animator.setDuration(10000L);
    animator.addUpdateListener(
        new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            final float progress = (float) animation.getAnimatedValue();
            final float width1 = backgroundOne.getWidth();
            final float width2 = backgroundThree.getWidth();
            final float translationX1 = width1 * progress;
            final float translationX2 = width2 * progress - 10;
            backgroundOne.setTranslationX(-translationX1);
            backgroundTwo.setTranslationX(-translationX1 + width1);
            backgroundThree.setTranslationX(-translationX2);
            backgroundFour.setTranslationX(-translationX2 + width2);
          }
        });
    animator.start();
  }

  /**
   * Updates the names of the save slot buttons, depending on whether if there is any data saved
   * into that slot.
   */
  private void updateButtons() {
    int initialPlayer = currPlayer;
    boolean has_file = false;
    for (String s : fileList()) {
      if (s.equals(STATS_FILE)) {
        has_file = true;
      }
    }
    if (!has_file) {
      startFile();
    }
    for (int i = 0; i < buttons.length; i++) {
      currPlayer = i;
      Button b = buttons[i];
      if (hasSavedFile()) {
        b.setText(getName());
      } else {
        b.setText(R.string.empty_save);
      }
    }
    currPlayer = initialPlayer;
  }

  /**
   * Sets the current player to the save slot being clicked, or open up character configuration if
   * an empty save slot is clicked.
   *
   * @param view The button that is clicked.
   */
  public void clickSave(View view) {
    Button b = (Button) view;
    for (int i = 0; i < buttons.length; i++) {
      Button currButton = buttons[i];
      if (currButton == b) {
        currPlayer = i;
      }
    }
    if (!hasSavedFile()) {
      Intent intent = new Intent(this, SetCharacterName.class);
      startActivityForResult(intent, 1);
    } else {
      updatePlayerInfo();
    }
  }

  /**
   * Calls the method to ask whether the user wants to delete the current slot when the delete
   * button is clicked.
   *
   * @param view The delete button.
   */
  public void clickDelete(View view) {
    if (hasSavedFile()) {
      queryDelete();
    }
  }

  /**
   * Asks the player if they want to resume/start the game once the start game button is clicked.
   *
   * @param view The button that is clicked.
   */
  public void clickStart(View view) {
    if (hasSavedFile()) {
      queryResume();
    }
  }

  /**
   * Allows the user to edit the preferences of the current save slot when the edit preferences
   * button is clicked.
   *
   * @param view The edit preferences button.
   */
  public void clickEdit(View view) {
    if (hasSavedFile()) {
      setPreferences();
    }
  }

  /** Updates the TextView with the stats of the current player. */
  private void updatePlayerInfo() {
    TextView display = findViewById(R.id.textView);
    if (hasSavedFile()) {
      int score = getScore();
      int health = getHealth();
      int coin = getCoin();
      findViewById(R.id.delete_a).setVisibility(View.VISIBLE);
      findViewById(R.id.delete_but).setVisibility(View.VISIBLE);
      findViewById(R.id.delt).setVisibility(View.VISIBLE);

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

      String name = getName();
      String stats =
          "Player: "
              + name
              + "\nScore: "
              + score
              + "\nHealth: "
              + health
              + "\nCoin: "
              + coin
              + "\n\nCustomizations:"
              + "\nScene: "
              + nightDay
              + "\nDifficulty: "
              + difficulty
              + "\nCharacter: "
              + character;
      if (getHighScore() > 0) {
        stats += "\n\nHighscore: " + getHighScore() + "\nObtained on: " + getHighScoreTime();
      }
      display.setText(stats);
    } else {
      findViewById(R.id.delete_a).setVisibility(View.INVISIBLE);
      findViewById(R.id.delete_but).setVisibility(View.INVISIBLE);
      findViewById(R.id.delt).setVisibility(View.INVISIBLE);
      display.setText(R.string.no_player);
    }
  }

  /**
   * Handles the info when a user submits a name. Calls additional method to prompt user to
   * personalize their settings.
   *
   * @param requestCode The requestCode that the request was sent out with.
   * @param resultCode Whether the request was successful or not.
   * @param data The Intent that the data was sent back in.
   */
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String userName = data.getStringExtra(sendName);
        setName(userName);
        setSaveStatus(true);
        setPreferences();
      }
    }
  }

  /** Starts a new activity to allow users to set their preferences. */
  private void setPreferences() {
    Intent intent = new Intent(this, PreferenceEditor.class);
    intent.putExtra(sendPlayer, currPlayer);
    startActivity(intent);
  }

  /** Asks the user if they want to start the game, or resume the game that they were playing. */
  private void queryResume() {
    final String[] choices = {"Yes", "No"};
    final Intent intent = new Intent(this, TradingScreen.class);

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    if (getScore() == 0) {
      builder.setTitle("Would you like to start the game?");
    } else {
      builder.setTitle("Would you like to resume your game?");
    }
    builder.setItems(
        choices,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
              if (getScore() > 0 && getHealth() > 0) {
                intent.putExtra(sendPlayer, currPlayer);
                startActivity(intent);
              } else {
                startGame();
              }
            }
          }
        });
    builder.show();
  }

  /** If the user does not want to start the game, ask if the user wants to delete the save slot. */
  private void queryDelete() {
    final String[] choices = {"Yes", "No"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    String prompt = "Would you like to delete \"" + getName() + "\"?";
    builder.setTitle(prompt);
    builder.setItems(
        choices,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
              deleteSlot();
              updateButtons();
              updatePlayerInfo();
            }
          }
        });
    builder.show();
  }

  /** Updates the buttons to show the correct score when back button is pressed. */
  @Override
  public void onResume() {
    super.onResume();
    updateButtons();
    updatePlayerInfo();
  }
}
