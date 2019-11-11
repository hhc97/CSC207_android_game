package com.example.phase1.BackendStorage;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase1.Level1Game.Level1Activity;
import com.example.phase1.Level2Game.Level2Activity;
import com.example.phase1.Level3Game.Level3Activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class is responsible for storing all stats into a local file, so as to be able to resume
 * games, and track statistics. As a result, all activities in this game will extend this class.
 *
 * @author Haocheng Hu
 */
public abstract class GameManager extends AppCompatActivity {
  static final String STATS_FILE = "stats.txt";
  public static final String sendPlayer = "com.example.phase1.BackendStorage.SEND_PLAYER";

  //    score, health, coin, day/night, difficulty, character, current level, player name
  String defaultScore = "0,3,0,3,0,0,1,NAME";
  int currPlayer = -1;

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
   * @param string The string that is to be written.
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

  /**
   * Returns the requested stat of the current player in String form.
   *
   * @param index The index of the requested stat.
   * @return A String that represents the stat.
   */
  private String getStringStat(int index) {
    String[] scores = readFromFile().split("\n");
    String[] stats = scores[currPlayer].split(",");
    return stats[index];
  }

  //  does the same as above except returns an int instead
  private int getStat(int index) {
    return Integer.parseInt(getStringStat(index));
  }

  /**
   * Takes in a string (which represents a stat), and writes it into the save file.
   *
   * @param value The value of the stat to be written.
   * @param index The index which the stat is stored.
   */
  private void setStringStat(String value, int index) {
    String[] scores = readFromFile().split("\n");
    String[] stats = scores[currPlayer].split(",");
    stats[index] = value;
    scores[currPlayer] = String.join(",", stats);
    writeToFile(String.join("\n", scores));
  }

  //  same as above except takes in an int
  private void setStat(int value, int index) {
    setStringStat(String.valueOf(value), index);
  }

  /** Deletes the save slot associated with the current player. */
  void deleteSlot() {
    String[] scores = readFromFile().split("\n");
    scores[currPlayer] = defaultScore;
    writeToFile(String.join("\n", scores));
    currPlayer = -1;
  }

  // getters and setters for all the stats
  public void setCurrPlayer(int i) {
    if (currPlayer == -1) {
      currPlayer = i;
    }
  }

  // for name
  void setName(String name) {
    setStringStat(name, playerName);
  }

  String getName() {
    return getStringStat(playerName);
  }

  // for score
  public int getScore() {
    return getStat(score);
  }

  public void setScore(int s) {
    setStat(s, score);
  }

  public void addScore(int s) {
    setScore(getScore() + s);
  }

  public void deductScore(int s) {
    setScore(getScore() - s);
  }

  // for health
  public int getHealth() {
    return getStat(health);
  }

  public void setHealth(int h) {
    setStat(h, health);
  }

  public void addHealth(int h) {
    setHealth(getHealth() + h);
  }

  public void deductHealth(int h) {
    setHealth(getHealth() - h);
  }

  // for coin
  public int getCoin() {
    return getStat(coin);
  }

  public void setCoin(int c) {
    setStat(c, coin);
  }

  public void addCoin(int c) {
    setCoin(getCoin() + c);
  }

  public void deductCoin(int c) {
    setCoin(getCoin() - c);
  }

  // for level
  private int getLevel() {
    return getStat(currentLevel);
  }

  private void setLevel(int level) {
    setStat(level, currentLevel);
  }

  // for day or night
  public int getDayOrNight() {
    return getStat(dayOrNight);
  }

  void setDayOrNight(int day) {
    setStat(day, dayOrNight);
  }

  // for difficulty
  public int getDifficulty() {
    return getStat(difficulty);
  }

  void setDifficulty(int d) {
    setStat(d, difficulty);
  }

  // for character
  public int getCharacter() {
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
      intent.putExtra(sendPlayer, currPlayer);
      startActivity(intent);
    } else if (level == 2) {
      Intent intent = new Intent(this, Level2Activity.class);
      intent.putExtra(sendPlayer, currPlayer);
      startActivity(intent);
    } else if (level == 3) {
      Intent intent = new Intent(this, Level3Activity.class);
      intent.putExtra(sendPlayer, currPlayer);
      startActivity(intent);
    }
  }

  /** Starts the next level, if current level is not the final level. */
  public void startNextLevel() {
    if (getLevel() < 3) {
      setLevel(getLevel() + 1);
      startGame();
    }
  }

  /** Starts the game again, if the player loses all their lives. */
  public void startAgain() {
    setLevel(1);
    setScore(0);
    setHealth(3);
    startGame();
  }

  /** If no save file exists, create a new one with default values. */
  void startFile() {
    writeToFile("0,3,0,0,2,0,1,Level 1");
    writeToFile(readFromFile() + "0,3,0,0,2,0,2,Level 2");
    writeToFile(readFromFile() + "0,3,0,0,2,0,3,Level 3");
    writeToFile(readFromFile() + defaultScore);
    writeToFile(readFromFile() + defaultScore);
    writeToFile(readFromFile() + defaultScore);
    //    writeToFile(defaultScore);
    //    for (int i = 0; i < 5; i++) {
    //      writeToFile(readFromFile() + defaultScore);
    //    }
  }
}
