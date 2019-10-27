package com.example.phase1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

/**
 * The activity responsible for displaying the save menu, where users can choose a slot to save, and
 * also resume previous saves.
 */
public class SaveMenu extends GameManager {

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
   * updates the names of the save slot buttons, depending on whether if there is any data saved
   * into that slot.
   */
  private void updateButtons() {
    Button initialButton = currButton;
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
      currButton = buttons[i];
      if (!scores[i].equals(defaultScore)) {
        String name = getName();
        String score = String.valueOf(getScore());
        String display = name + ", Score: " + score;
        currButton.setText(display);
      }
    }
    currButton = initialButton;
  }

  /**
   * determines what do do when a save slot is clicked. either load the game if the slot has data,
   * or allow the user to create a new account if the slot is empty.
   *
   * @param view the button that is clicked.
   */
  public void clickSave(View view) {
    Button b = (Button) view;
    currButton = b;
    String buttonName = b.getText().toString();
    if (buttonName.equals("Empty save slot")) {
      Intent intent = new Intent(this, SetCharacterName.class);
      startActivityForResult(intent, 1);
    } else {
      startGame();
    }
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String userName = data.getStringExtra("com.example.phase1.SEND_NAME");
        setName(userName);

        final String[] dayNight = {"Day", "Night"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color scheme");
        builder.setItems(
            dayNight,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                if (dayNight[which].equals("Night")) {
                  setDayOrNight(0);
                } else if (dayNight[which].equals("Day")) {
                  setDayOrNight(1);
                }
                updateButtons();
              }
            });
        builder.show();
      }
    }
  }
}
