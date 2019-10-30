package com.example.phase1;

import java.util.ArrayList;
import java.util.Random;

class Sequence {

  private static Random random;

  public Sequence() {
    random = new Random();
  }

  public static ArrayList<Integer> getSequence() {
    ArrayList<Integer> sequence = new ArrayList<Integer>();
    for (int i = 0; i < 4; i++) {
      sequence.add(random.nextInt(4));
    }

    return sequence;
  }

  public static ArrayList<Integer> getSequence(int difficulty) {
    ArrayList<Integer> sequence = new ArrayList<Integer>();
    for (int i = 0; i < difficulty; i++) {
      sequence.add(random.nextInt(4));
    }

    return sequence;
  }
}
