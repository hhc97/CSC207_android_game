package com.example.phase1.BackendStorage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    startFile(); // for testing, resets save activity each time app is launched. Comment it out to
    // test save functionality.
    setContentView(R.layout.activity_save_menu);
    Button save1 = findViewById(R.id.button3);
    Button save2 = findViewById(R.id.button4);
    Button save3 = findViewById(R.id.button5);
    Button save4 = findViewById(R.id.button6);
    Button save5 = findViewById(R.id.button7);
    Button save6 = findViewById(R.id.button8);
    buttons = new Button[] {save1, save2, save3, save4, save5, save6};
    updateButtons();
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
      if (getSaveStatus()) {
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
        currButton.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
      } else {
        currButton.getBackground().clearColorFilter();
      }
    }
    if (!getSaveStatus()) {
      Intent intent = new Intent(this, SetCharacterName.class);
      startActivityForResult(intent, 1);
    } else {
      updatePlayerInfo();
    }
  }

  /**
   * Checks if the current selected save slot has any data saved.
   *
   * @return Returns true if the save slot contains data, false otherwise.
   */
  private boolean canEdit() {
    if (currPlayer == -1) {
      return false;
    }
    return getSaveStatus();
  }

  /**
   * Calls the method to ask whether the user wants to delete the current slot when the delete
   * button is clicked.
   *
   * @param view The delete button.
   */
  public void clickDelete(View view) {
    if (canEdit()) {
      queryDelete();
    }
  }

  /**
   * Asks the player if they want to resume/start the game once the start game button is clicked.
   *
   * @param view The button that is clicked.
   */
  public void clickStart(View view) {
    if (canEdit()) {
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
    if (canEdit()) {
      setPreferences();
    }
  }

  /** Updates the TextView with the stats of the current player. */
  private void updatePlayerInfo() {
    TextView display = findViewById(R.id.textView);
    if (currPlayer != -1 && getSaveStatus()) {
      int score = getScore();
      int health = getHealth();
      int coin = getCoin();

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
      display.setText(stats);
    } else {
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
              startGame();
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
