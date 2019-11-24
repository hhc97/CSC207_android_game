package com.example.phase1.BackendStorage;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class reads and writes to and from a local save file for an activity.
 *
 * @author Haocheng Hu
 */
abstract class FileReadWriter extends AppCompatActivity {
  static final String STATS_FILE = "stats.txt";

  /**
   * Write to the file the string that's passed to it.
   *
   * @param string The string that is to be written.
   */
  void writeToFile(String string) {
    PrintWriter out = null;
    try {
      OutputStream outStream = openFileOutput(STATS_FILE, Context.MODE_PRIVATE);
      out = new PrintWriter(outStream);
    } catch (FileNotFoundException e) {
      assert true;
    }
    out.println(string);
    out.close();
  }

  /**
   * Reads from the saved data file and returns what's stored.
   *
   * @return The string that is stored.
   */
  String readFromFile() {
    StringBuilder builder = new StringBuilder();
    try (Scanner scanner = new Scanner(openFileInput(STATS_FILE))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        builder.append(line).append('\n');
      }
    } catch (IOException e) {
      assert true;
    }
    return builder.toString();
  }
}
