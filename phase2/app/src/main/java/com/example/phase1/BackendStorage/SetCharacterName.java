package com.example.phase1.BackendStorage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase1.R;

/**
 * A class to get the player name and send it back to SaveMenu.
 *
 * @author Haocheng Hu
 */
public class SetCharacterName extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_character_name);
  }

  /**
   * Puts the name String into an Intent and sends its back.
   *
   * @param view The button that is pressed.
   */
  public void sendName(View view) {
    Intent intent = new Intent();
    EditText editText = findViewById(R.id.editText2);
    String message = editText.getText().toString();
    if (message.equals("")) {
      return;
    }
    message = message.replace(",", ".");
    if (message.length() > 9) {
      message = message.substring(0, 9);
    }
    intent.putExtra(SaveMenu.sendName, message);
    setResult(RESULT_OK, intent);
    finish();
  }
}
