package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SaveMenu extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
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

  private void updateButtons() {
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
        buttons[i].setText(display);
      }
    }
  }

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
        updateButtons();
      }
    }
  }
}
