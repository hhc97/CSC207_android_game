package com.example.phase1.Level1Game;

import com.example.phase1.Objects.Coin;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Hero;
import com.example.phase1.Objects.Monster;
import com.example.phase1.Objects.ObjectBuilder;
import com.example.phase1.Objects.Potion;

import java.util.ArrayList;

/** a manager for level1 that resolves calculations occurred in the gameplay. */
class Level1Manager {
  ArrayList<GameObject> Objects = new ArrayList<>();
  private Hero player;
  private float playerStartX = 50; // temp, the x coordinate of character.
  private float groundHeight = 20; // temp, the height of the ground.
  private float playerStartY = groundHeight;
  private int difficulty = 0; // default difficulty
  private float maxFrameSize = 1800; // default max frame size
  // (tailored for pixel 3 xl)
  private float minFrameSize = -50; // default minimum frame size
  private ObjectBuilder builder;
  private boolean hasGotTheEasterEgg = false;

  /** @param difficulty int value to represent difficulty */
  public Level1Manager(int difficulty) {
    this.difficulty = difficulty;
    builder = new ObjectBuilder(this.difficulty);
    setupObjects();
  }

  /** default constructor with no parameter */
  public Level1Manager() {
    builder = new ObjectBuilder(this.difficulty);
    setupObjects();
  }

  private void setupObjects() { // instantiate the objects with default values
    player = (Hero) builder.createObject("Hero");
    player.setPosition(playerStartX, playerStartY);
    player.setHealth(100);
    Monster m1 = (Monster) builder.createObject("Monster");
    m1.setPosition(800, groundHeight);
    m1.setStrength(1);
    m1.setSpeed(5);
    Monster m2 = (Monster) builder.createObject("Monster");
    m2.setPosition(1250, groundHeight);
    m2.setStrength(1);
    m2.setSpeed(5);
    m2.setTracingHero(false);
    Monster m3 = (Monster) builder.createObject("Monster");
    m3.setPosition(1750, groundHeight);
    m3.setStrength(1);
    m3.setSpeed(10);
    m3.setTracingHero(false);
    Coin c1 = (Coin) builder.createObject("Coin");
    c1.setPosition(1000, groundHeight);
    Coin c2 = (Coin) builder.createObject("Coin");
    c2.setPosition(1500, groundHeight);
    Coin c3 = (Coin) builder.createObject("Coin");
    c3.setPosition(1750, groundHeight);
    Potion p = (Potion) builder.createObject("Potion");
    p.setPosition(400, groundHeight);

    Objects.add(player); // add objects to the Objects list
    Objects.add(m1);
    Objects.add(m2);
    Objects.add(m3);
    Objects.add(c1);
    Objects.add(c2);
    Objects.add(c3);
    Objects.add(p);
    if (this.difficulty == 0) {
      m2.die();
      m2.setWorth(0);
      m3.die();
      m3.setWorth(0);
    } else if (this.difficulty == 1) {
      m3.die();
      m3.setWorth(0);
    }
  }

  /**
   * setter for maximum framesize x for all GameObjects
   *
   * @param x the maximum x value of the frame
   */
  public void setMaxFrameSize(float x) {
    this.maxFrameSize = x;
    for (GameObject obj : Objects) {
      obj.setMaxFrameSize(this.maxFrameSize);
    }
  }

  /**
   * etter for minimum framesize x for all GameObjects
   *
   * @param x the minimum x value of the frame
   */
  public void setMinFrameSize(float x) {
    this.minFrameSize = x;
    for (GameObject obj : Objects) {
      obj.setMinFrameSize(this.minFrameSize);
    }
  }

  /** resolve move right action in player class */
  public void rightButtonPress() {
    if (isPlayerAlive()) {
      player.moveRight(); // resolve moving right in player class
    }
  }

  /** resolve move left action in player class */
  public void leftButtonPress() {
    if (isPlayerAlive()) {
      player.moveLeft(); // resolve moving left in player class
    }
  }

  /** resolve attack action in player class */
  public void attackButtonPress() {
    if (isPlayerAlive()) { // if player is alive
      player.attack(); // resolve attack in player class
    }
  }

  /** resolve potion usage in player class */
  public void usePotionButtonPress() {
    player.usePotion();
  }
  // method for communicating to player class that a potion has been used

  public boolean isWinning() {
    boolean isWon = true;
    for (int i = 1; i < Objects.size(); i++) { // if all the GameObjects are dead
      if (Objects.get(i).getStates()) isWon = false; // if any of them isn't, isWon = false
    }
    return isWon;
  }
  // Update every object in the array list.
  public void update() {
    // Update every object in the array list.
    for (GameObject obj : Objects) {
      obj.update();
    }
    if (player.isAttack()) {
      player.notAttack();
    }
    checkEasterEgg();
  }

  /**
   * getter for player
   *
   * @return instance of Hero class used as player for the current level
   */
  public Hero getPlayer() {
    return player;
  }

  /**
   * setter for difficulty, 0 = easy, 1 = medium, 2 = hard
   *
   * @param difficulty int variable to represent difficulty
   */
  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }
  // setter for difficulty

  /**
   * getter for list Objects
   *
   * @return ArrayList of GameObjects Objects in this class
   */
  public ArrayList getObjects() {
    return this.Objects;
  }

  /** @return if player is alive or not */
  public boolean isPlayerAlive() {
    return player.getStates();
  }

  private void checkEasterEgg() {
    if (easterEggCondition()) {
      easterEgg();
    }
  }

  private boolean easterEggCondition() {
    if (difficulty == 2 && !hasGotTheEasterEgg) {
      // if difficulty is hard and easteregg hasn't triggered in this game yet
      if (isPlayerAlive() && player.getHealth() <= 25) {
        // if player has less than 25 health
        for (int i = 4; i <= 6; i++) { // and none of the coins are collected
          if (!Objects.get(i).getStates()) return false;
        }
        return true;
      }
    }
    return false;
  }

  private void easterEgg() {
    hasGotTheEasterEgg = true;
    player.addPotion();
    player.addScore(500);
  }

  /** @return if player has already gotten the easter egg or not */
  public boolean hasGotTheEasterEgg() {
    return hasGotTheEasterEgg;
  }
}
