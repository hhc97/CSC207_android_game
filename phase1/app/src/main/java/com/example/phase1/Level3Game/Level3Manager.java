package com.example.phase1.Level3Game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for comparing the user input sequence to the actual sequence for stage
 * 3 of the game.
 */
public class Level3Manager {

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
  }

  /**
   * Check if the user has made an error in the input of the sequence
   *
   * @return true if the user input does not match the sequence
   */
  public boolean checkError() {
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
  public boolean checkWin() {
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