package com.example.phase1.Level3Game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for comparing the user input sequence to the actual sequence for stage
 * 3 of the game.
 */
class Level3Manager {

  private static ArrayList<Integer> sequence;
  static int attempts = 0;
  // button inputs are stored as primitive int from 0-3 inclusive in clockwise order (from 0-3)
  private static ArrayList<Integer> input = new ArrayList<>();

  /** Constructor initializes sequence. */
  Level3Manager() {
    sequence = Sequence.getSequence(); // get a new Sequence from Sequence class
  }

  /**
   * Getter for ArrayList sequence.
   *
   * @return The stored sequence
   */
  static ArrayList<Integer> getSequence() {
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
  static void setUserInput(int pressed) {
    input.add(pressed);
  }

  static void clearInput() {
    input.clear();
  }

  /**
   * Check if the user has won or inputted the wrong sequence
   *
   * @return 1 if user input does not match sequence, 2 if the user has won, else 0
   */
  static int checkConditions() {
    Iterator<Integer> seq = sequence.iterator();
    Iterator<Integer> in = input.iterator();

    while (seq.hasNext() && in.hasNext()) {
      int a = seq.next();
      int b = in.next();
      if (a != b) {
        attempts++;
        return 1;
      }
      if (!seq.hasNext() && !in.hasNext()) {
        return 2;
      }
    }
    return 0;
  }
}
