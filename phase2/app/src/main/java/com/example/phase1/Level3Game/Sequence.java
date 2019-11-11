package com.example.phase1.Level3Game;

import java.util.ArrayList;
import java.util.Random;

class Sequence {

  public Sequence() {}

  /**
   * Gets a random sequence of integers from 0-3 inclusive without repeated values
   *
   * @return the random sequence of integers
   */
  static ArrayList<Integer> getSequence() {
    Random random = new Random();
    ArrayList<Integer> sequence = new ArrayList<>();
    ArrayList<Integer> x = new ArrayList<>();
    x.add(0);
    x.add(1);
    x.add(2);
    x.add(3);
    for (int i = 0; i < 4; i++) {
      int randindex = random.nextInt(x.size());
      int item = x.get(randindex);
      sequence.add(item);
      x.remove((Integer) item);
      //      sequence.add(random.nextInt(i));
    }

    return sequence;
  }
  //
  //  /**
  //   * An alternative getSequence method with difficulty setting to be implemented in the future
  //   * @param difficulty The difficulty setting of the current game
  //   * @return  a random sequence of integers
  //   */
  //  static ArrayList<Integer> getSequence(int difficulty) {
  //    ArrayList<Integer> sequence = new ArrayList<Integer>();
  //    Random random = new Random();
  //    for (int i = 0; i < difficulty; i++) {
  //      sequence.add(random.nextInt(4));
  //    }
  //
  //    return sequence;
  //  }
}
