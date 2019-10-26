package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SaveMenu extends GameManager {
  private Button currButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_save_menu);
    Button save1 = (Button) findViewById(R.id.button3);
    Button save2 = (Button) findViewById(R.id.button4);
    Button save3 = (Button) findViewById(R.id.button5);
    Button save4 = (Button) findViewById(R.id.button6);
    Button save5 = (Button) findViewById(R.id.button7);
    Button save6 = (Button) findViewById(R.id.button8);
    Button[] buttons = {save1, save2, save3, save4, save5, save6};
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
      if (!scores[i].equals("0")) {
        buttons[i].setText("Score: " + scores[i]);
      }
    }
  }

  public void clickSave(View view) {
    Button b = (Button) view;
    String buttonName = b.getText().toString();
    if (buttonName.equals("Empty save slot")) {
      currButton = b;
      Intent intent = new Intent(this, SetCharacterName.class);
      startActivityForResult(intent, 1);
    }
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String userName = data.getStringExtra("com.example.phase1.SEND_NAME");
        currButton.setText(userName);
      }
    }
  }

  public void testLevel1(View view) {
    Intent intent = new Intent(this, Level1Activity.class);
    startActivity(intent);
  }

  public void testLevel2(View view) {
    Intent intent = new Intent(this, Level2Activity.class);
    startActivity(intent);
  }
}
