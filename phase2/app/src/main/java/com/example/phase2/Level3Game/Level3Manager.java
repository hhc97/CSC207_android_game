package com.example.phase2.Level3Game;

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
  private static ArrayList<Integer> input = new ArrayList<>();

  void setAttempts(int i){attempts = i;}
  /** Constructor initializes sequence. */
  Level3Manager(int i) {
    Sequence seq = new Sequence(); // get a new Sequence from Sequence class
    sequence = seq.getSequence(i);
  }

  /**
   * Getter for ArrayList sequence.
   *
   * @return The stored sequence
   */
  ArrayList<Integer> getSequence()
  {
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
   * Check if the user has won or inputted the wrong sequence
   *
   * @return 1 if user input does not match sequence, 2 if the user has won, else 0
   */
  int checkConditions() {
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

  int getAttempts() {
    return attempts;
  }
}
