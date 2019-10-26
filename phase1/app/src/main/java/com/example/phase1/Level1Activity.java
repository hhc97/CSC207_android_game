package com.example.phase1;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Level1Activity extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Fullscreen Window Without Bar at the top
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    // Remove the Title
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(R.layout.activity_level1);
  }
}
