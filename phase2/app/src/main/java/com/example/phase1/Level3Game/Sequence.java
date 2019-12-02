package com.example.phase1.Level3Game;

import java.util.ArrayList;
import java.util.Random;

class Sequence {

  private int difficulty;
  private int length;
  private int toComplete;

  Sequence(int i) {
    difficulty = i;
    setLength();
    toComplete = i + 1;
  }

  void complete() {
    toComplete -= 1;
  }

  int getLength() {
    return length;
  }

  int getToComplete() {
    return toComplete;
  }

  private void setLength() {
    switch (difficulty) {
      case 0:
        length = 4;
        break;
      case 1:
        length = 6;
        break;
      case 2:
        length = 12;
        break;
    }
  }
  /**
   * Gets a random sequence of integers from 0-3 inclusive without repeated values
   *
   * @return the random sequence of integers
   */
  ArrayList<Integer> getSequence() {
    Random random = new Random();
    ArrayList<Integer> sequence = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      sequence.add(random.nextInt(4));
    }

    return sequence;
  }

  int getDifficulty() {
    return difficulty;
  }
}
