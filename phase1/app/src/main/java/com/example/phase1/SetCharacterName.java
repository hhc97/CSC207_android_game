package com.example.phase1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetCharacterName extends AppCompatActivity {
  public static final String NAME = "com.example.phase1.NAME";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_character_name);
  }
  public void sendName(View view) {
    Intent intent = new Intent(this, SaveMenu.class);
    EditText editText = findViewById(R.id.editText);
    String message = editText.getText().toString();
    intent.putExtra(NAME, message);
    startActivity(intent);
  }
}
