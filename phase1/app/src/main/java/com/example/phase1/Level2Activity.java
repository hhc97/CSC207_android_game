package com.example.phase1;

import android.app.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Level2Activity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set our window to fullscreen without the bar at the top.
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
  }
}
