package com.example.phase1.BackendStorage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.phase1.R;
import com.example.phase1.SetCharacterName;

/**
 * The activity responsible for displaying the save menu, where users can choose a slot to save, and
 * also resume previous saves.
 */
public class SaveMenu extends GameManager {
  private String emptySlot = "Empty save slot";
  private Button[] buttons;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    startFile(); // for testing, resets save file each time
    super.onCreate(savedInstanceState);
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
    String[] scores = readFromFile().split("\n");
    for (int i = 0; i < scores.length; i++) {
      currPlayer = i;
      Button b = buttons[i];
      if (!scores[i].equals(defaultScore)) {
        String name = getName();
        String score = String.valueOf(getScore());
        String display = name + ", Score: " + score;
        b.setText(display);
      } else {
        b.setText(emptySlot);
      }
    }
    currPlayer = initialPlayer;
  }

  /**
   * Determines what do do when a save slot is clicked. Either ask the user if they want to load the
   * game/delete the save slot, or allow the user to create a new account if the slot is empty.
   *
   * @param view the button that is clicked.
   */
  public void clickSave(View view) {
    Button b = (Button) view;
    for (int i = 0; i < buttons.length; i++) {
      if (buttons[i] == b) {
        currPlayer = i;
      }
    }
    String buttonName = b.getText().toString();
    if (buttonName.equals(emptySlot)) {
      Intent intent = new Intent(this, SetCharacterName.class);
      startActivityForResult(intent, 1);
    } else {
      queryResume();
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
        String userName = data.getStringExtra("com.example.phase1.SEND_NAME");
        setName(userName);
        queryDayNight();
      }
    }
  }

  /** Prompts the user to select day or night. */
  private void queryDayNight() {
    final String[] dayNight = {"Night", "Day"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Pick a color scheme");
    builder.setCancelable(false);
    builder.setItems(
        dayNight,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            setDayOrNight(which);
            queryDifficulty();
          }
        });
    builder.show();
  }

  /** Prompts the user to select the game difficulty. */
  private void queryDifficulty() {
    final String[] difficulty = {"Easy", "Normal", "Hard"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Select a difficulty");
    builder.setCancelable(false);
    builder.setItems(
        difficulty,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            setDifficulty(which + 1);
            queryCharacter();
          }
        });
    builder.show();
  }

  /** Prompts the user to select the character they want to play as. */
  private void queryCharacter() {
    final String[] characters = {"Rogue", "Knight"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Pick a character");
    builder.setCancelable(false);
    builder.setItems(
        characters,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            setCharacter(which);
            updateButtons();
          }
        });
    builder.show();
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
    builder.setCancelable(false);
    builder.setItems(
        choices,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
              startGame();
            } else {
              queryDelete();
            }
          }
        });
    builder.show();
  }

  /** If the user does not want to start the game, ask if the user wants to delete the save slot. */
  private void queryDelete() {
    final String[] choices = {"Yes", "No"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Would you like to delete this save slot?");
    builder.setCancelable(false);
    builder.setItems(
        choices,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
              deleteSlot();
              updateButtons();
            }
          }
        });
    builder.show();
  }
}
