package com.example.phase1;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is responsible for storing all stats into a local file, so as to be able to resume
 * games, and track statistics. As a result, all activities in this game will extend this class.
 */
abstract class GameManager extends AppCompatActivity {
  static final String STATS_FILE = "stats.txt";

  //    score, health, coin, day/night, difficulty, character, current level, player name
  String defaultScore = "0,0,0,0,1,0,1,NAME";
  int currPlayer;
  Button[] buttons;

  //  indexes of the player statistics
  private int score = 0;
  private int health = 1;
  private int coin = 2;
  private int dayOrNight = 3;
  private int difficulty = 4;
  private int character = 5;
  private int currentLevel = 6;
  private int playerName = 7;

  /**
   * Write to the file the string that's passed to it.
   *
   * @param string the string that is to be written.
   */
  private void writeToFile(String string) {
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
   * Reads from the save data file and returns what's stored.
   *
   * @return the string that is stored.
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

  private String getStringStat(int index) {
    String[] scores = readFromFile().split("\n");
    String[] stats = scores[currPlayer].split(",");
    return stats[index];
  }

  private int getStat(int index) {
    return Integer.parseInt(getStringStat(index));
  }

  private void setStringStat(String value, int index) {
    String[] scores = readFromFile().split("\n");
    String[] stats = scores[currPlayer].split(",");
    stats[index] = value;
    scores[currPlayer] = String.join(",", stats);
    writeToFile(String.join("\n", scores));
  }

  private void setStat(int value, int index) {
    setStringStat(String.valueOf(value), index);
  }

  // getters and setters for all the stats
  void setName(String name) {
    setStringStat(name, playerName);
  }

  String getName() {
    return getStringStat(playerName);
  }

  int getScore() {
    return getStat(score);
  }

  void setScore(int s) {
    setStat(s, score);
  }

  int getHealth() {
    return getStat(health);
  }

  void setHealth(int h) {
    setStat(h, health);
  }

  int getCoin() {
    return getStat(coin);
  }

  void setCoin(int c) {
    setStat(c, coin);
  }

  int getLevel() {
    return getStat(currentLevel);
  }

  private void setLevel(int level) {
    setStat(level, currentLevel);
  }

  int getDayOrNight() {
    return getStat(dayOrNight);
  }

  void setDayOrNight(int day) {
    setStat(day, dayOrNight);
  }

  int getDifficulty() {
    return getStat(difficulty);
  }

  void setDifficulty(int d) {
    setStat(d, difficulty);
  }

  int getCharacter() {
    return getStat(character);
  }

  void setCharacter(int c) {
    setStat(c, character);
  }

  /**
   * Starts the game, depending on which level the player was previously on. Starts at level 1 if
   * it's a new player.
   */
  void startGame() {
    int level = getLevel();
    if (level == 1) {
      Intent intent = new Intent(this, Level1Activity.class);
      intent.putExtra("com.example.phase1.SEND_PLAYER", currPlayer);
      startActivity(intent);
    } else if (level == 2) {
      Intent intent = new Intent(this, Level2Activity.class);
      intent.putExtra("com.example.phase1.SEND_PLAYER", currPlayer);
      startActivity(intent);
    } else if (level == 3) {
      Intent intent = new Intent(this, Level3Activity.class);
      intent.putExtra("com.example.phase1.SEND_PLAYER", currPlayer);
      startActivity(intent);
    }
  }

  /** Starts the next level, if current level is not the final level. */
  void nextLevel() {
    if (getLevel() < 3) {
      setLevel(getLevel() + 1);
      startGame();
    }
  }

  /** If no save file exists, create a new one with default values. */
  void startFile() {
    writeToFile("0,0,0,0,0,0,1,LV1");
    writeToFile(readFromFile() + "0,0,0,0,0,0,2,LV2");
    writeToFile(readFromFile() + "0,0,0,0,0,0,3,LV3");
    writeToFile(readFromFile() + defaultScore);
    writeToFile(readFromFile() + defaultScore);
    writeToFile(readFromFile() + defaultScore);
  }
}
