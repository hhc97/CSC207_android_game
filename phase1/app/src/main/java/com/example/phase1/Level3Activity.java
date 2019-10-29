package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.phase1.BackendStorage.GameManager;

public class Level3Activity extends GameManager {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra("com.example.phase1.SEND_PLAYER", 0));

    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
            .setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    if (getDayOrNight() == 0) {
      setContentView(R.layout.n_activity_level3);
    } else if (getDayOrNight() == 1) {
      setContentView(R.layout.activity_level3);
    }
  }
}
