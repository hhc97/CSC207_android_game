package com.example.phase1;

import android.content.Context;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

abstract class GameManager extends AppCompatActivity {
  static final String STATS_FILE = "stats.txt";
  private int score = 0;
  Button currButton;
  Button[] buttons;

  private void writeToFile(String string) {
    PrintWriter out = null;

    try {
      OutputStream outStream = openFileOutput(STATS_FILE, Context.MODE_PRIVATE);
      out = new PrintWriter(outStream);
    } catch (FileNotFoundException e) {
    }
    out.println(string);
    out.close();
  }

  /**
   * Read and return the information in EXAMPLE_FILE.
   *
   * @return the contents of EXAMPLE_FILE.
   */
  String readFromFile() {
    StringBuilder builder = new StringBuilder();
    try (Scanner scanner = new Scanner(openFileInput(STATS_FILE))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        builder.append(line).append('\n');
      }
    } catch (IOException e) {
    }

    return builder.toString();
  }

  int getStat(int index) {
    int indexOfButton = 0;
    for (int i = 0; i < buttons.length; i++) {
      if (buttons[i] == currButton) {
        indexOfButton = i;
      }
    }
    String[] scores = readFromFile().split("\n");
    String[] stats = scores[indexOfButton].split(",");
    return Integer.parseInt(stats[index]);
  }

  void setStat(int value, int index) {
    int indexOfButton = 0;
    for (int i = 0; i < buttons.length; i++) {
      if (buttons[i] == currButton) {
        indexOfButton = i;
      }
    }
    String[] scores = readFromFile().split("\n");
    String[] stats = scores[indexOfButton].split(",");
    stats[index] = String.valueOf(value);
    scores[indexOfButton] = String.join(",", stats);
    writeToFile(String.join("\n", scores));
  }

  int getScore() {
    return getStat(0);
  }

  void setScore(int s) {
    setStat(s, 0);
  }

  int getHealth() {
    return getStat(1);
  }

  void setHealth(int h) {
    setStat(h, 1);
  }

  int getCoin() {
    return getStat(2);
  }

  void setCoin(int c) {
    setStat(c, 2);
  }

  void test() {
    System.out.println(readFromFile());
  }

  void startFile() {
    writeToFile("0,0,0");
    writeToFile(readFromFile() + "0,0,0");
    writeToFile(readFromFile() + "0,0,0");
    writeToFile(readFromFile() + "1,253,30");
    writeToFile(readFromFile() + "0,0,0");
    writeToFile(readFromFile() + "9,50,26");
  }
}
