package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
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

/** This class is the Activity Class for Level 3 */
public class Level3Activity extends GameManager implements View.OnClickListener {

  private Level3Manager Level3 = new Level3Manager(); // Current level
  private DisplayHandler displayHandler = new DisplayHandler();
  private Timer timer = new Timer("Timer"); // Timer

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCurrPlayer(getIntent().getIntExtra(sendPlayer, 0));

    // Set our window to fullscreen without the bar at the top.
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Remove the title.
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    // Set day or night setting

    if (getDayOrNight() == 0) {
      setContentView(R.layout.n_activity_level3);
    }
    else{
      setContentView(R.layout.activity_level3);
    }
    // Set static hero near door
    final ImageView hero = findViewById(R.id.hero);
    if (getCharacter() == 0) {
      hero.setVisibility(View.VISIBLE);
    }
    else {
      hero.setVisibility(View.INVISIBLE);
    }

    Button[] buttons = new Button[4];
    // initialise button components
    buttons[0]= findViewById(R.id.b1);
    buttons[1]= findViewById(R.id.b2);
    buttons[2]= findViewById(R.id.b3);
    buttons[3]= findViewById(R.id.b4);

    displayHandler.setButtons(buttons);
    displayHandler.setOut((TextView)findViewById(R.id.pstat));
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
    displayHandler.startSequence();
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
                        displayHandler.setButtonVisibile(i); // Make button Visible
                      }
                    });
              }

            } else {
              cancel();
            }
          }
        };
    timer.schedule(task, 1000L, 1000L); // schedule the task to execute every second
    displayHandler.endSequence();
  }

  /**
   * Sends user input to the Level3Manager. Executes error and win conditional tasks.
   *
   * @param v Any button that is clicked
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == DisplayHandler.getButton(0).getId()) { // Top left button clicked
      Level3.setUserInput(0);
    }
    if (v.getId() == DisplayHandler.getButton(1).getId()) { // Bottom left button clicked
      Level3.setUserInput(1);
    }
    if (v.getId() == DisplayHandler.getButton(2).getId()) { // Bottom right button clicked
      Level3.setUserInput(2);
    }
    if (v.getId() == DisplayHandler.getButton(3).getId()) { // Top right button clicked
      Level3.setUserInput(3);
    }
    if (Level3.checkConditions() == 1) { // User did not input correct sequence
      on_bad_input();
    }
    if (Level3.checkConditions() == 2) { // User successfully inputs correct sequence
      on_win();
    }
  }

  /** Set all Button.Enabled and Clickable properties to false* */
  private void disable_buttons() {
    DisplayHandler.set_buttons_invisible();
  }

  /** Set Text for Textview out and change Textview out.Visible property to VISIBLE* */
  private void set_text(final String s) {
    runOnUiThread(
        new Runnable() { // force task to run on UI Thread
          @Override
          public void run() {
            DisplayHandler.set_text(s);
          }
        });
  }

  /**
   * To be called when a sequence is incorrectly inputted. Deducts lives and displays sequence again
   * if the player has any remaining lives, else it restarts the game.
   */
  private void on_bad_input(){
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
                      new Runnable() { // delay the task by 2 seconds
                        @Override
                        public void run() {
                          displaySequence();
                        }
                      },
                      2000); // 2000ms = 2 seconds
    }
  }

  /**
   * To be called when the correct sequence has been inputted. Displays a message and ends the game.
   */
  private void on_win() {
    disable_buttons();
    set_text("Correct Pattern!, You win! Returning to the Save Menu");
    new Handler()
            .postDelayed(
                    new Runnable() { // delay the task by 3 seconds
                      @Override
                      public void run() {
                        finish();
                      }
                    },
                    3000); // 3000ms = 3 seconds
  }
}
