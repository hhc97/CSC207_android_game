package com.example.phase1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for comparing the user input sequence to the actual sequence for stage
 * 3 of the game.
 */
public class Level3Manager {

  private ArrayList<Integer> sequence;
  private ArrayList<Integer> input;
  /* private int attempts = 0; */

  public Level3Manager() {
    sequence = Sequence.getSequence();
  }

  /**
   * An alternative constructor with a specified difficulty level for future use.
   *
   * @param difficulty
   */
  public Level3Manager(int difficulty) {
    sequence = Sequence.getSequence(difficulty);
  }

  public void getuserinput(int pressed) {
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
}
