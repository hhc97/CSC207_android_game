package com.example.phase1.BackendStorage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase1.R;

/** The main activity that starts the game and loads into the save menu. */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * Load into the save menu when the start game button is clicked.
   *
   * @param view The start game button.
   */
  public void startGame(View view) {
    Intent intent = new Intent(this, SaveMenu.class);
    startActivity(intent);
  }
}
