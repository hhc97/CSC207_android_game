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

  private Timer timer = new Timer("Timer"); // Timer
  private Level3Facade level3Facade = new Level3Facade();

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
    } else {
      setContentView(R.layout.activity_level3);
    }
    // Set static hero near door
    final ImageView hero = findViewById(R.id.hero);
    if (getCharacter() == 0) {
      hero.setVisibility(View.VISIBLE);
    } else {
      hero.setVisibility(View.INVISIBLE);
    }

    Button[] buttons = new Button[4];
    // initialise components
    buttons[0] = findViewById(R.id.b1);
    buttons[1] = findViewById(R.id.b2);
    buttons[2] = findViewById(R.id.b3);
    buttons[3] = findViewById(R.id.b4);
    Button key = findViewById(R.id.keybutton);
    TextView magicText = findViewById(R.id.magic_text);
    TextView pstat = findViewById(R.id.pstat);
    TextView health = findViewById(R.id.health);
    TextView potions = findViewById(R.id.potions);
    TextView score = findViewById(R.id.score);

    level3Facade.setDisplayHandler(
        new DisplayHandler(buttons, pstat, magicText, key, health, potions, score));
    level3Facade.setLevel3(new Level3Manager(getDifficulty()));
    level3Facade.updateHealth(getHealth());
    level3Facade.updatePotions(getPotion());
    level3Facade.updateScore(getScore());
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
  void displaySequence() {
    level3Facade.startSequence();
    final Iterator<Integer> sequence = level3Facade.getSequence().iterator();

    final TimerTask task = new TimerTask() { // create TimerTask
          @Override
          public void run() {
            if (sequence.hasNext()) { // check that the iterator has more items.
              final int i = sequence.next();
              {
                runOnUiThread(
                    new Runnable() { // force task to run on UI Thread
                      @Override
                      public void run() {
                        level3Facade.setButtonInvisible(i); // Make button Visible
                      }
                    });
              }

            } else {
              cancel();
            }
          }
        };
    final TimerTask task1 = new TimerTask() { // create TimerTask
          @Override
          public void run() {
            runOnUiThread(
                new Runnable() { // force task to run on UI Thread
                  @Override
                  public void run() {
                    level3Facade.setButtonsVisible(); // Make button Visible
                  }
                });
          }
        };
    timer.schedule(task, 500L, 1000L); // schedule the task to execute every second
    timer.schedule(task1, 1000L, 1000L);
    new Handler()
        .postDelayed(
            new Runnable() { // delay the task by 5 seconds
              @Override
              public void run() {
                level3Facade.endSequence();
                if (getBonusKeys() > 0) {
                  level3Facade.showKeyButton();
                  level3Facade.enableKeyButton();
                  level3Facade.showKeyText();
                }
              }
            },
            (level3Facade.getLength() * 1000) + 500);
  }

  /**
   * Allows users to use bonus keys if they have any
   *
   * @param v Bonus key button
   */
  public void useBonusKey(View v) {
    if (getBonusKeys() > 0) {
      setBonusKeys(getBonusKeys() - 1);
      level3Facade.disableKeyButton();
      onCorrectSequence();
    } else {
      level3Facade.disableKeyButton();
      setText("You don't have any bonus keys. Please input the sequence.");
    }
  }
  /**
   * Sends user input to the Level3Manager. Executes error and win conditional tasks.
   *
   * @param v Sequence button that is clicked
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == level3Facade.getButton(0).getId()) { // Top left button clicked
      level3Facade.setUserInput(0);
    }
    if (v.getId() == level3Facade.getButton(1).getId()) { // Bottom left button clicked
      level3Facade.setUserInput(1);
    }
    if (v.getId() == level3Facade.getButton(2).getId()) { // Bottom right button clicked
      level3Facade.setUserInput(2);
    }
    if (v.getId() == level3Facade.getButton(3).getId()) { // Top right button clicked
      level3Facade.setUserInput(3);
    }
    if (level3Facade.checkConditions() == 1) { // User did not input correct sequence
      onBadInput();
    }
    if (level3Facade.checkConditions() == 2) { // User successfully inputs correct sequence
      onCorrectSequence();
    }
  }

  /** Set Text for Textview out and change Textview out.Visible property to VISIBLE* */
  private void setText(final String s) {
    runOnUiThread(
        new Runnable() { // force task to run on UI Thread
          @Override
          public void run() {
            level3Facade.setText(s);
          }
        });
  }

  /**
   * To be called when a sequence is incorrectly inputted. Deducts lives and displays sequence again
   * if the player has any remaining lives, else it restarts the game.
   */
  private void onBadInput() {
    level3Facade.disableButtons();

    if (level3Facade.getAttempts()
        >= 3 - level3Facade.getDifficulty()) { // User made maximum unpunishable attempts
      int x = (level3Facade.getDifficulty() + 3) * 10; // Amount of health to deduct
      deductHealth(x); // deduct hp
      level3Facade.updateHealth(getHealth());
      if (getHealth() <= 0) // restart game if they run out of lives
      {
        if (getPotion() > 0) {
          level3Facade.setAttempts(0);
          setPotion(getPotion() - 1);
          level3Facade.updatePotions(getPotion());
          setText("Using a potion. " + getPotion() + "Potions remaining.");
          new Handler()
              .postDelayed(
                  new Runnable() { // delay the task by 2 seconds
                    @Override
                    public void run() {
                      displaySequence();
                    }
                  },
                  2000);
        }
        setText("You have 0 potions and 0 health remaining. You have died. Restarting game.");
        level3Facade.updateHealth(0);
        new Handler()
            .postDelayed(
                new Runnable() { // delay the task by 2 seconds
                  @Override
                  public void run() {
                    finish();
                  }
                },
                2000);

      } else {
        setText(
            "Incorrect Pattern! You ran out of attempts, -"
                + x
                + " Health. You have "
                + getHealth()
                + " Health remaining.");
        level3Facade.setAttempts(0);
        new Handler()
            .postDelayed(
                new Runnable() { // delay the task by 2 seconds
                  @Override
                  public void run() {
                    displaySequence(); // display sequence if they still have lives remaining
                  }
                },
                2000);
      }

    } else { // User input incorrect sequence but still has remaining attempts
      setText(
          "Incorrect Pattern! "
              + (3 - level3Facade.getAttempts())
              + "attempts remaining! "
              + "Displaying Sequence");
      level3Facade.clearInput(); // clear input for next attempt
      level3Facade.disableButtons();
      new Handler()
          .postDelayed(
              new Runnable() { // delay the task by 2 seconds
                @Override
                public void run() {
                  displaySequence();
                }
              },
              2000);
    }
  }

  /**
   * To be called when the correct sequence has been inputted. Displays a message and ends the game.
   */
  private void onCorrectSequence() {
    level3Facade.disableButtons();
    level3Facade.completeSequence();
    if (level3Facade.getToComplete() == 0) { // User wins
      setText("Correct Pattern! You win! Returning to the Save Menu");
      new Handler()
          .postDelayed(
              new Runnable() { // delay the task by 3 seconds
                @Override
                public void run() {
                  finish();
                }
              },
              3000); // 3000ms = 3 seconds
    } else {
      setText(
          "Correct Pattern! You have "
              + level3Facade.getToComplete()
              + " patterns left to complete.");
      addScore(10 * level3Facade.getLength());
      level3Facade.updateScore(getScore());
      new Handler()
          .postDelayed(
              new Runnable() {
                public void run() {
                  displaySequence();
                }
              },
              2000);
    }
  }
}
