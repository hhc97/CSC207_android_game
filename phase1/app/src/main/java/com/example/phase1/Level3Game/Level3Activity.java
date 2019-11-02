package com.example.phase1.Level3Game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.R;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Level3Activity extends GameManager implements View.OnClickListener {

  private static Level3Manager Level3 = new Level3Manager();
  private Button[] buttons = new Button[4];
    private Timer timer = new Timer("Timer");

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
  }

    protected void onStart() {
        super.onStart();
        displaySequence();
        System.out.println("Success");
    }
    public void displaySequence() {
        final Iterator<Integer> sequence = Level3.getSequence().iterator();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (sequence.hasNext()) {
                    final int i = sequence.next();
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttons[i].setVisibility(View.VISIBLE);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                } else {
                    cancel();
                }
            }
        };
        long period = 1000L;
        long delay = 1000L;
        timer.schedule(task, delay, period);
    }

  @Override
  public void onClick(View v) {
      if (v.getId() == buttons[0].getId()) {
          Level3.getUserInput(0);
      }
      if (v.getId() == buttons[1].getId()) {
          Level3.getUserInput(1);
      }
      if (v.getId() == buttons[2].getId()) {
          Level3.getUserInput(2);
      }
      if (v.getId() == buttons[3].getId()) {
          Level3.getUserInput(3);
      }
      if (Level3.checkError()) {
          TextView out = findViewById(R.id.pstat);
          out.setVisibility(View.VISIBLE);
      }
      if (Level3.checkWin()) {
          TextView out = findViewById(R.id.pstat);
          out.setText("Correct Pattern!");
          out.setVisibility(View.VISIBLE);
      }
  }
}
