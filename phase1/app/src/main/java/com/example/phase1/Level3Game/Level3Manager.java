package com.example.phase1.Level3Game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for comparing the user input sequence to the actual sequence for stage
 * 3 of the game.
 */
class Level3Manager {

  private ArrayList<Integer> sequence;
  int attempts = 0;
  // button inputs are stored as primitive int from 0-3 inclusive in clockwise order (from 0-3)
  private ArrayList<Integer> input = new ArrayList<>();

  /** Constructor initializes sequence. */
  Level3Manager() {
    sequence = Sequence.getSequence(); // get a new Sequence from Sequence class
  }

  /**
   * Getter for ArrayList sequence.
   *
   * @return The stored sequence
   */
  ArrayList<Integer> getSequence() {
    return sequence;
  }
  //  /**
  //   * An alternative constructor with a specified difficulty level for future use.
  //   *
  //   * @param difficulty Difficulty level of current game
  //   */
  //  public Level3Manager(int difficulty) {
  //    sequence = Sequence.getSequence(difficulty);
  //  }

  /**
   * add a user input to ArrayList input
   *
   * @param pressed the button which was pressed
   */
  void setUserInput(int pressed) {
    input.add(pressed);
  }

  void clearInput() {
    input.clear();
  }

  /**
   * Check if the user has made an error in the input of the sequence
   *
   * @return true if the user input does not match the sequence
   */
  boolean checkError() {
    Iterator<Integer> seq = sequence.iterator();
    Iterator<Integer> in = input.iterator();

    while (seq.hasNext() && in.hasNext()) {
      int a = seq.next();
      int b = in.next();
      if (a != b) {
        attempts++;
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
  boolean checkWin() {
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
