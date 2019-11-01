package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.phase1.BackendStorage.GameManager;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Level3Activity extends GameManager implements View.OnClickListener {

  private static Level3Manager Level3 = new Level3Manager();
  private Button[] buttons = new Button[4];
    private Timer timer = new Timer("Timer");
    private long delay = 1000L;
    private long period = 1000L;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra(sendPlayer, 0));

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
      buttons[0] = findViewById(R.id.b1);
      buttons[1] = findViewById(R.id.b2);
      buttons[2] = findViewById(R.id.b3);
      buttons[3] = findViewById(R.id.b4);

    } else {
      buttons[0] = findViewById(R.id.b28);
      buttons[1] = findViewById(R.id.b29);
      buttons[2] = findViewById(R.id.b30);
      buttons[3] = findViewById(R.id.b31);
    }
      displaySequence();
  }

    public void displaySequence() {
        final Iterator<Integer> sequence = Level3.getSequence().iterator();
        buttons[0].setVisibility(View.VISIBLE);
        buttons[1].setVisibility(View.VISIBLE);
        buttons[2].setVisibility(View.VISIBLE);
        buttons[3].setVisibility(View.VISIBLE);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (sequence.hasNext()) {
                    int i = sequence.next();
                    buttons[i].setVisibility(View.INVISIBLE);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
      }
        };
        timer.schedule(task, delay, period);
    }

  @Override
  public void onClick(View v) {
    Level3.onClick(v);
  }
}
