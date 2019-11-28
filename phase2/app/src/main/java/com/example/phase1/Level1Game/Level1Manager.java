package com.example.phase1.Level1Game;

import com.example.phase1.Objects.*;

import java.util.ArrayList;

public class Level1Manager {
  ArrayList<GameObject> Objects = new ArrayList<>();
  private static Hero player;
  private float playerStartX = 50; // temp, the x coordinate of character.
  private float groundHeight = 20; // temp, the height of the ground.
  private float playerStartY = groundHeight;
  private int difficulty = 0; // default difficulty
  private int dayOrNight = 0; // default background
  private float maxFrameSize = 1800;
  private float minFrameSize = -50;
  private ObjectBuilder builder;

  public Level1Manager(int difficulty) {
    this.difficulty = difficulty;
    builder = new ObjectBuilder(this.difficulty);
    setupObjects();
  }

  public Level1Manager() {
    builder = new ObjectBuilder(this.difficulty);
    setupObjects();
  }

  private void setupObjects() {
    player = (Hero) builder.createObject("Hero");
    player.setPosition(playerStartX, playerStartY);
    player.setHealth(100);
    Monster m1 = (Monster) builder.createObject("Monster");
    m1.setPosition(800, groundHeight);
    m1.setStrength(1);
    m1.setSpeed(20);
    Monster m2 = (Monster) builder.createObject("Monster");
    m2.setPosition(1250, groundHeight);
    m2.setStrength(1);
    m2.setSpeed(20);
    m2.setTracingHero(false);
    Monster m3 = (Monster) builder.createObject("Monster");
    m3.setPosition(1750, groundHeight);
    m3.setStrength(1);
    m3.setSpeed(30);
    m3.setTracingHero(false);
    Coin c1 = (Coin) builder.createObject("Coin");
    c1.setPosition(1000, groundHeight);
    Coin c2 = (Coin) builder.createObject("Coin");
    c2.setPosition(1500, groundHeight);
    Coin c3 = (Coin) builder.createObject("Coin");
    c3.setPosition(1750, groundHeight);
    Potion p = (Potion) builder.createObject("Potion");
    p.setPosition(400, groundHeight);

    Objects.add(player);
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

  public void setMaxFrameSize(float x) {
    this.maxFrameSize = x;
    for (GameObject obj : Objects) {
      obj.setMaxFrameSize(this.maxFrameSize);
    }
  }

  public void setMinFrameSize(float x) {
    this.minFrameSize = x;
    for (GameObject obj : Objects) {
      obj.setMinFrameSize(this.minFrameSize);
    }
  }

  public void rightButtonPress() {
    if (player.getStates()) {
      player.moveRight();
      update();
    }
  }

  public void leftButtonPress() {
    if (player.getStates()) {
      player.moveLeft();
      update();
    }
  }

  public void attackButtonPress() {
    if (player.getStates()) {
      player.attack();
      update();
      player.notAttack();
    }
  }

  public void usePotionButtonPress() {
    player.usePotion();
  }

  public void jumpButtonPress() {}

  public boolean isWinning() {
    boolean isWon = true;
    for (int i = 1; i < Objects.size(); i++) { // if all the GameObjects are dead
      if (Objects.get(i).getStates()) isWon = false; // if any of them isn't, isWon = false
    }
    return isWon;
  }
  // Update every object in the array list.
  private void update() {
    // Update every object in the array list.
    for (GameObject obj : Objects) {
      obj.update();
    }
  }

  public static Hero getPlayer() {
    return player;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public void setDayOrNight(int dayOrNight) {
    this.dayOrNight = dayOrNight;
  }

  public ArrayList getObjects() {
    return this.Objects;
  }

  public boolean isPlayerAlive() {
    return player.getStates();
  }
}
