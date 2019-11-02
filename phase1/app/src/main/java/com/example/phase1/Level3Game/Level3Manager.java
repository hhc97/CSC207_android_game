package com.example.phase1.Level3Game;

import android.view.View;

import com.example.phase1.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for comparing the user input sequence to the actual sequence for stage
 * 3 of the game.
 */
public class Level3Manager implements View.OnClickListener {

  private ArrayList<Integer> sequence;
  private ArrayList<Integer> input = new ArrayList<>();
  /* private int attempts = 0; */

    Level3Manager() {
    sequence = Sequence.getSequence();
  }

    ArrayList<Integer> getSequence() {
    return sequence;
  }
  /**
   * An alternative constructor with a specified difficulty level for future use.
   *
   * @param difficulty
   */
  public Level3Manager(int difficulty) {
    sequence = Sequence.getSequence(difficulty);
  }

  public void getUserInput(int pressed) {
    input.add(pressed);
    if (this.checkWin()) {
      /*Do something when game has been completed*/
    }
    if (checkIn()) {
      /* Implement a pop-up that prompts the user to either reload a previous checkpoint
       * or end the game after 3 failed attempts at the sequence(They lost).*/
    }
  }

  /**
   * Check if the user has made an error in the input of the sequence
   *
   * @return true if the user input does not match the sequence
   */
  private boolean checkIn() {
    Iterator<Integer> seq = sequence.iterator();
    Iterator<Integer> in = input.iterator();

    while (seq.hasNext() && in.hasNext()) {
      int a = seq.next();
      int b = in.next();
      if (a != b) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the user has won
   *
   * @return true if user input does not match sequence
   */
  private boolean checkWin() {
    Iterator<Integer> seq = sequence.iterator();
    Iterator<Integer> in = input.iterator();

    while (seq.hasNext() && in.hasNext()) {
      seq.next();
      in.next();
      if (!seq.hasNext() && !in.hasNext()) {
        return true;
      }
    }
    return false;
  }

  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.b1:
      case R.id.b28:
        getUserInput(0);
        break;

      case R.id.b2:
      case R.id.b29:
        getUserInput(1);
        break;

      case R.id.b3:
      case R.id.b30:
        getUserInput(2);
        break;

      case R.id.b4:
      case R.id.b31:
        getUserInput(3);
        break;

      default:
        throw new RuntimeException("Unknown button ID");
    }
  }
}
