package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.BackendStorage.MainActivity;
import com.example.phase1.R;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/** This class is the Activity Class for Level 3 */
public class Level3Activity extends GameManager implements View.OnClickListener {

  private Level3Manager Level3 = new Level3Manager(); // Current level
  private Button[] buttons = new Button[4]; // Array of buttons for the current layout
  private Timer timer = new Timer("Timer"); // Timer
  private TextView out;

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
    out = findViewById(R.id.pstat);
  }

  /** Override for Activity.onStart. Displays the Sequence. */
  protected void onStart() {
    super.onStart();
    displaySequence();
  }

  /**
   * Get the sequence from the current level and displays it with a 1 second delay between each
   * button.
   */
  @SuppressLint("SetTextI18n")
  public void displaySequence() {
    disable_buttons(); // disable the buttons while the sequence is displaying
    set_buttons_invisible();
    out.setText("Wait for the sequence to display");
    out.setVisibility(View.VISIBLE);
    final Iterator<Integer> sequence = Level3.getSequence().iterator();

    TimerTask task = new TimerTask() { // create TimerTask
          @Override
          public void run() {
            if (sequence.hasNext()) { // check that the iterator has more items.
              final int i = sequence.next();
              {
                runOnUiThread(
                    new Runnable() { // force task to run on UI Thread
                      @Override
                      public void run() {
                        buttons[i].setVisibility(View.VISIBLE); // Make button Visible
                      }
                    });
              }

            } else {
              cancel();
            }
          }
        };
    timer.schedule(task, 1000L, 1000L); // schedule the task to execute every second
    enable_buttons(); // enable buttons after sequence is displayed
    out.setText("Start!");
    out.setVisibility(View.VISIBLE);
  }

  /**
   * Sends user input to the Level3Manager. Executes error and win conditional tasks.
   *
   * @param v Any button that is clicked
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == buttons[0].getId()) { // Top left button clicked
      Level3.setUserInput(0);
    }
    if (v.getId() == buttons[1].getId()) { // Bottom left button clicked
      Level3.setUserInput(1);
    }
    if (v.getId() == buttons[2].getId()) { // Bottom right button clicked
      Level3.setUserInput(2);
    }
    if (v.getId() == buttons[3].getId()) { // Top right button clicked
      Level3.setUserInput(3);
    }
    if (Level3.checkError()) { // User did not input correct sequence
      if (Level3.attempts == 3) { // User made 3 attempts (out of attempts)
        deductHealth(1); // deduct a life
          set_text("Incorrect Pattern! You ran out of attempts, -1 lives");
        Level3.attempts = 0;
        new Handler()
            .postDelayed(
                new Runnable() { // delay the task by 5 seconds
                  @Override
                  public void run() {
                    if (getHealth() == 0) // restart game if they run out of lives
                    {
                      startAgain();
                    }
                    onStart(); // restart level if they still have lives remaining
                  }
                },
                5000); // 5000ms = 5 seconds

      } else { // User input incorrect sequence but still has remaining attempts
          set_text(
            "Incorrect Pattern! " + (3 - Level3.attempts) + " remaining! " + "Displaying Sequence");
        Level3.clearInput(); // clear input for next attempt
        disable_buttons();
        new Handler()
            .postDelayed(
                new Runnable() { // delay the task by 5 seconds
                  @Override
                  public void run() {
                    displaySequence();
                  }
                },
                2000); // 2000ms = 2 seconds
      }
    }
    if (Level3.checkWin()) { // User successfully inputs correct sequence
      disable_buttons();
        set_text("Correct Pattern!, You win! Returning to the Main Menu");
      new Handler()
          .postDelayed(
              new Runnable() { // delay the task by 5 seconds
                @Override
                public void run() {
                  finish();
                }
              },
              5000); // 5000ms = 5 seconds
    }
  }

    /**
     * Set all Button.Enabled and Clickable properties to true
     */
    private void enable_buttons() {
        runOnUiThread(
                new Runnable() { // force task to run on UI Thread
                    @Override
                    public void run() {
                        for (Button button : buttons) {
                            button.setEnabled(true);
                            button.setClickable(true);
                        }
                    }
                });
    }

    /**
     * Set all Button.Visible properties to INVISIBLE*
     */
    private void set_buttons_invisible() {
        runOnUiThread(
                new Runnable() { // force task to run on UI Thread
                    @Override
                    public void run() {
                        for (Button button : buttons) {
                            button.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

  /** Set all Button.Enabled and Clickable properties to false* */
  private void disable_buttons() {
      for (Button button : buttons) {
          button.setEnabled(false);
          button.setClickable(false);
      }
  }

    /**
     * Set Text for Textview out and change Textview out.Visible property to VISIBLE*
     */
    private void set_text(final String s) {
        runOnUiThread(
                new Runnable() { // force task to run on UI Thread
                    @Override
                    public void run() {
                        out.setText(s);
                        out.setVisibility(View.VISIBLE);
                    }
                });
    }

  public void onStop() {
    super.onStop();
    startActivity(new Intent(this, MainActivity.class));
  }
}
