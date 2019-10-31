package com.example.phase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.phase1.BackendStorage.GameManager;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Level3Activity extends GameManager implements View.OnClickListener {

  private static Level3Manager Level3 = new Level3Manager();
  private Button[] buttons = new Button[4];

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    setCurrPlayer(intent.getIntExtra(sendCharacter, 0));

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
    main();
  }

  public void main() {
    ArrayList<Integer> sequence = Level3.getSequence();
    Timestamp now = new Timestamp(0);
    //    long timenow;
    for (int i = 0; i < sequence.size(); i++) {
      //      timenow = now.getTime();
      Button b = buttons[sequence.get(i)];
      System.out.println(sequence.get(i));
      b.setVisibility(View.INVISIBLE);
      findViewById(R.id.n_activity3).invalidate();
      try {
        Thread.sleep(500);
        System.out.println("paused");
      } catch (InterruptedException e) {
        System.out.println("OOPSIES");
      }
      b.setVisibility(View.VISIBLE);
      findViewById(R.id.n_activity3).invalidate();
    }
  }
  //
  //  private void bandAid(final View view) {
  //    view.setVisibility(View.INVISIBLE);
  //    view.postDelayed(
  //        new Runnable() {
  //          public void run() {
  //            view.setVisibility(View.VISIBLE);
  //          }
  //        },
  //        2000);
  //    }

  //    buttons[0].setVisibility(View.INVISIBLE);
  //    buttons[1].setVisibility(View.VISIBLE);
  //    buttons[2].setVisibility(View.VISIBLE);
  //    buttons[3].setVisibility(View.VISIBLE);

  //
  //    final Iterator<Integer> sequence = Level3.getSequence().iterator();
  // ;
  //      TimerTask displayNext = new TimerTask(){
  //        public void run(){
  //          buttons[0].setVisibility(View.VISIBLE);
  //          buttons[1].setVisibility(View.VISIBLE);
  //          buttons[2].setVisibility(View.VISIBLE);
  //          buttons[3].setVisibility(View.VISIBLE);
  //
  //          if(sequence.hasNext()){
  //            int item = sequence.next();
  //            switch(item){
  //              case 0:
  //                buttons[0].setVisibility(View.INVISIBLE);
  //                break;
  //              case 1:
  //                buttons[1].setVisibility(View.INVISIBLE);
  //                break;
  //              case 2:
  //                buttons[2].setVisibility(View.INVISIBLE);
  //                break;
  //              case 3:
  //                buttons[3].setVisibility(View.INVISIBLE);
  //                break;
  //              default:
  //                System.out.println("Something went wrong. Check Sequence Class for error.");
  //                break;
  //            }
  //          }
  //      }
  //      };
  //    Timer timer = new Timer("Timer");
  //    long delay = 1000L;
  //    long period = 1000L;
  //    timer.scheduleAtFixedRate(displayNext, delay, period);

  @Override
  public void onClick(View v) {
    Level3.onClick(v);
  }
}
