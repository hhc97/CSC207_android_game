package com.example.phase1;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

abstract class GameManager extends AppCompatActivity {
  static final String STATS_FILE = "stats.txt";
  private int score = 0;

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

  int getScore() {
    return score;
  }

  void setScore(int s) {
    score = s;
  }

  void test() {
    System.out.println(readFromFile());
  }

  void startFile() {
    writeToFile("0");
    writeToFile(readFromFile() + "0");
    writeToFile(readFromFile() + "0");
    writeToFile(readFromFile() + "2");
    writeToFile(readFromFile() + "0");
    writeToFile(readFromFile() + "0");
  }
}
