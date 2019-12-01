package com.example.phase2.Level3Game;

import java.util.ArrayList;
import java.util.Random;

class Sequence {

  private int difficulty;

  void setDifficulty(int i){
    switch(i){
      case 0: this.difficulty = 5;
              break;
      case 1: this.difficulty = 10;
              break;
      case 2: this.difficulty = 15;
              break;
    }
  }
  /**
   * Gets a random sequence of integers from 0-3 inclusive without repeated values
   *
   * @return the random sequence of integers
   */
  ArrayList<Integer> getSequence(int j) {
    setDifficulty(j);
    Random random = new Random();
    ArrayList<Integer> sequence = new ArrayList<>();
    for (int i = 0; i < difficulty; i++) {
      sequence.add(random.nextInt(4));
    }

    return sequence;
  }
}
