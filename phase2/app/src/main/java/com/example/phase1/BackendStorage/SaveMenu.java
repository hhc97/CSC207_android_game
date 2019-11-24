package com.example.phase1.BackendStorage;

import android.content.DialogInterface;
import android.content.Intent;
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
  private final String emptySlot = "Empty save slot";
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
   * Sets the current player to the save slot being clicked, or open up character configuration if
   * an empty save slot is clicked.
   *
   * @param view The button that is clicked.
   */
  public void clickSave(View view) {
    Button b = (Button) view;
    for (int i = 0; i < buttons.length; i++) {
      if (buttons[i] == b) {
        currPlayer = i;
      }
    }
    if (b.getText().toString().equals(emptySlot)) {
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
    return !buttons[currPlayer].getText().toString().equals(emptySlot);
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
   * Asks the player if they want to resume/start the level once the start game button is clicked.
   *
   * @param view The button that is clicked.
   */
  public void clickStart(View view) {
    queryResume();
  }

  public void clickEdit(View view) {
    setPreferences();
  }

  /** Updates the TextView with the stats of the current player. */
  private void updatePlayerInfo() {
    TextView display = findViewById(R.id.textView);
    if (currPlayer != -1 && !getName().equals("NAME")) {
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
        setPreferences();
      }
    }
  }

  private void setPreferences() {
    Intent intent = new Intent(this, PreferenceEditor.class);
    intent.putExtra(sendPlayer, currPlayer);
    startActivity(intent);
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
            setDifficulty(which);
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
            updatePlayerInfo();
            updateButtons();
          }
        });
    builder.show();
  }

  /** Asks the user if they want to start the game, or resume the game that they were playing. */
  private void queryResume() {
    if (currPlayer == -1) {
      return;
    }
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

  /** Asks the player if they would like to reconfigure their preferences. */
  private void queryEdit() {
    final String[] choices = {"Yes", "No"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Would you like to edit your preferences?");
    builder.setItems(
        choices,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
              queryDayNight();
            } else {
              queryDelete();
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
