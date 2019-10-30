package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.phase1.BackendStorage.GameManager;

public class Level3Activity extends GameManager implements View.OnClickListener {
    public static Level3Manager Level3 = new Level3Manager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra(sendString, 0));

    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    // Set day or night setting

    if (getDayOrNight() == 0) {
      setContentView(R.layout.n_activity_level3);
    } else if (getDayOrNight() == 1) {
      setContentView(R.layout.activity_level3);
    }
    // Set static hero near door
    final ImageView hero = findViewById(R.id.hero);
    if (getCharacter() == 0) {
      hero.setVisibility(View.VISIBLE);
    } else {
      hero.setVisibility(View.INVISIBLE);
    }

    // initialise button components
    if (getDayOrNight() == 0) {
      Button b1 = findViewById(R.id.b1);
      Button b2 = findViewById(R.id.b2);
      Button b3 = findViewById(R.id.b3);
      Button b4 = findViewById(R.id.b4);
    } else {
      Button b1 = findViewById(R.id.b28);
      Button b2 = findViewById(R.id.b29);
      Button b3 = findViewById(R.id.b30);
      Button b4 = findViewById(R.id.b31);
    }
  }


    @Override
    public void onClick(View v) {
        Level3.onClick(v);
    }
}
