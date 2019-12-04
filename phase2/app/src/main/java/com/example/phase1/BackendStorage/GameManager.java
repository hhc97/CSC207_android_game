package com.example.phase1.BackendStorage;

import android.content.Intent;

import com.example.phase1.Level1Game.Level1Activity;
import com.example.phase1.Level2Game.Level2Activity;
import com.example.phase1.Level3Game.Level3Activity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class extends FileReadWriter, and is responsible for retrieving and manipulating the data
 * that is stored. Individual activities will extend this class to gain access to its methods for
 * storing stats.
 *
 * @author Haocheng Hu
 */
public abstract class GameManager extends FileReadWriter {
  public static final String sendPlayer = "com.example.phase1.BackendStorage.SEND_PLAYER";

  // score, health, coin, night/day, difficulty, character, potion, bonus keys, current level,
  // player name, save status, high score, high score time
  final String defaultScore = "0,100,0,0,0,0,0,0,1,NAME,0,0,(time)";
  int currPlayer = -1;

  //  indexes of the player statistics
  private int score = 0;
  private int health = 1;
  private int coin = 2;
  private int dayOrNight = 3; // 0: night, 1: day
  private int difficulty = 4; // 0: easy, 1: normal, 2: hard
  private int character = 5; // 0: rogue, 1: knight
  private int potion = 6;
  private int bonusKeys = 7;
  private int currentLevel = 8; // 1: level 1, 2: level 2, 3: level 3
  private int playerName = 9;
  private int saveStatus = 10; // 0: empty slot, 1: slot has data
  private int highScore = 11;
  private int highScoreTime = 12;

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
  /**
   * Sets the current player to i, if the current player is not valid.
   *
   * @param i The value to set current player to.
   */
  public void setCurrPlayer(int i) {
    if (currPlayer == -1) {
      currPlayer = i;
    }
  }

  // for name
  /**
   * Sets the current player's name to name.
   *
   * @param name The name that is to be set.
   */
  void setName(String name) {
    setStringStat(name, playerName);
  }

  /**
   * Returns the name of the current player.
   *
   * @return The name of the current player.
   */
  String getName() {
    return getStringStat(playerName);
  }

  // for score
  /**
   * Returns the score of the current player.
   *
   * @return The score of the current player.
   */
  public int getScore() {
    return getStat(score);
  }

  /**
   * Sets the score of the current player. If the score is higher than the highscore, update the
   * highscore and the time and date which it was achieved.
   *
   * @param s The score to be set.
   */
  public void setScore(int s) {
    if (s > getHighScore()) {
      setHighScore(s);
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
      LocalDateTime now = LocalDateTime.now();
      String timeNow = dtf.format(now);
      setHighScoreTime(timeNow);
    }
    setStat(s, score);
  }

  /**
   * Adds the input value to the current player's score.
   *
   * @param s The value to increase score by.
   */
  public void addScore(int s) {
    setScore(getScore() + s);
  }

  /**
   * Deducts the input value from the current player's score.
   *
   * @param s The value to decrease score by.
   */
  public void deductScore(int s) {
    setScore(getScore() - s);
  }

  // for health
  /**
   * Returns the health of the current player.
   *
   * @return The health of the current player.
   */
  public int getHealth() {
    return getStat(health);
  }

  /**
   * Sets the current player's health to the input value.
   *
   * @param h The value to set the health to.
   */
  public void setHealth(int h) {
    setStat(h, health);
  }

  /**
   * Decreases the current player's health by the input value.
   *
   * @param h Amount to decrease health by.
   */
  public void deductHealth(int h) {
    setHealth(getHealth() - h);
  }

  // for coin
  /**
   * Returns the number of coins the current player has.
   *
   * @return The number of coins.
   */
  public int getCoin() {
    return getStat(coin);
  }

  /**
   * Sets the current player's coin to c.
   *
   * @param c The value to set coin to.
   */
  public void setCoin(int c) {
    setStat(c, coin);
  }

  /**
   * Deducts c number of coins from the current player's inventory.
   *
   * @param c The number of coins to deduct.
   */
  public void deductCoin(int c) {
    setCoin(getCoin() - c);
  }

  // for level
  /**
   * Returns the current level.
   *
   * @return The current level.
   */
  private int getLevel() {
    return getStat(currentLevel);
  }

  /**
   * Sets the current level to the input value.
   *
   * @param level The value to set current level to.
   */
  private void setLevel(int level) {
    setStat(level, currentLevel);
  }

  // for day or night
  /**
   * Returns the current player's day/night preference.
   *
   * @return An int representing the day/night preference.
   */
  public int getDayOrNight() {
    return getStat(dayOrNight);
  }

  /**
   * Sets the day/night preference of the current player to the input value.
   *
   * @param day The value to set day/night to.
   */
  void setDayOrNight(int day) {
    setStat(day, dayOrNight);
  }

  // for difficulty
  /**
   * Returns the difficulty setting of the current player.
   *
   * @return The difficulty setting.
   */
  public int getDifficulty() {
    return getStat(difficulty);
  }

  /**
   * Sets the difficulty setting to the input value.
   *
   * @param d The value to set difficulty to.
   */
  void setDifficulty(int d) {
    setStat(d, difficulty);
  }

  // for character
  /**
   * Gets the current player's character preference.
   *
   * @return The character preference.
   */
  public int getCharacter() {
    return getStat(character);
  }

  /**
   * Sets the character preference of the current player to the input value.
   *
   * @param c The value to set character preference to.
   */
  void setCharacter(int c) {
    setStat(c, character);
  }

  //  for potion
  /**
   * Returns the number of potions the current player has.
   *
   * @return The number of potions the current player has.
   */
  public int getPotion() {
    return getStat(potion);
  }

  /**
   * Sets the number of potions that the current player has to the input value.
   *
   * @param p The value to set potions to.
   */
  public void setPotion(int p) {
    setStat(p, potion);
  }

  /** Adds one potion to the number of potions the current player has. */
  public void addPotion() {
    setPotion(getPotion() + 1);
  }

  //  for bonus keys
  /**
   * Returns the number of keys the current player has.
   *
   * @return The number of keys the current player has.
   */
  public int getBonusKeys() {
    return getStat(bonusKeys);
  }

  /**
   * Sets the current player's number of keys to the input value.
   *
   * @param k The value to set keys to.
   */
  public void setBonusKeys(int k) {
    setStat(k, bonusKeys);
  }

  /** Add one to the number of keys the current player has. */
  public void addKey() {
    setBonusKeys(getBonusKeys() + 1);
  }

  // for save status
  /**
   * Returns true if the current save slot is occupied.
   *
   * @return true if occupied, false if not.
   */
  boolean hasSavedFile() {
    return currPlayer != -1 && getStat(saveStatus) == 1;
  }

  /**
   * Sets the save status of the current slot to true or false, depending on the input.
   *
   * @param b The value to set save status to.
   */
  void setSaveStatus(boolean b) {
    int status = (b) ? 1 : 0;
    setStat(status, saveStatus);
  }

  // for high score
  /**
   * Returns the current player's all time high score.
   *
   * @return The current player's all time high score.
   */
  int getHighScore() {
    return getStat(highScore);
  }

  /**
   * Gets called when the player's score exceeds their all time best.
   *
   * @param hs The new all time best score to set highscore to.
   */
  private void setHighScore(int hs) {
    setStat(hs, highScore);
  }

  // for high score time
  /**
   * Returns the time of which the all time best score was obtained.
   *
   * @return The time of which the all time best score was obtained.
   */
  String getHighScoreTime() {
    return getStringStat(highScoreTime);
  }

  /**
   * Sets the time which the all time best score was obtained to the input value.
   *
   * @param hst The time which the all time best score was obtained.
   */
  private void setHighScoreTime(String hst) {
    setStringStat(hst, highScoreTime);
  }

  /**
   * Starts the game, depending on which level the player was previously on. Starts at level 1 if
   * it's a new player.
   */
  void startGame() {
    if (getHealth() <= 0) {
      startAgain();
    }
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

  /** Restarts the level. */
  public void restartLevel() {
    startGame();
  }

  /** Starts the next level, if current level is not the final level. */
  public void startNextLevel() {
    if (getLevel() < 3) {
      setLevel(getLevel() + 1);
      startGame();
    }
  }

  /**
   * Starts a specified level, for when the player can skip levels.
   *
   * @param level The level that is to be started.
   */
  public void startSpecifiedLevel(int level) {
    if (level >= 1 && level <= 3) {
      setLevel(level);
      startGame();
    }
  }

  /** Starts the game again, if the player loses all their lives. */
  public void startAgain() {
    setLevel(1);
    setScore(0);
    setPotion(0);
    setBonusKeys(0);
    setCoin(0);
    setHealth(100);
    startGame();
  }

  /** If no save file exists, create a new one with default values. */
  void startFile() {
    writeToFile(defaultScore);
    for (int i = 0; i < 5; i++) {
      writeToFile(readFromFile() + defaultScore);
    }
  }
}
