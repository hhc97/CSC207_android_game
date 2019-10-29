package com.example.phase1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase1.BackendStorage.SaveMenu;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void startGame(View view) {
    Intent intent = new Intent(this, SaveMenu.class);
    startActivity(intent);
  }
}
