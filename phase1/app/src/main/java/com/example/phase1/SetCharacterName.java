package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SetCharacterName extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_character_name);
  }

  public void sendName(View view) {
    Intent intent = new Intent();
    EditText editText = findViewById(R.id.editText2);
    String message = editText.getText().toString();
    if (message.equals("")) {
      return;
    }
    message = message.replace(",", "_");
    intent.putExtra("com.example.phase1.SEND_NAME", message);
    setResult(RESULT_OK, intent);
    finish();
  }
}
