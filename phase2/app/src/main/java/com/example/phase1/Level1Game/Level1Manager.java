package com.example.phase1.Level1Game;

import com.example.phase1.Objects.Coin;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Hero;
import com.example.phase1.Objects.Monster;
import com.example.phase1.Objects.ObjectBuilder;

import java.util.ArrayList;

public class Level1Manager {
  ArrayList<GameObject> Objects = new ArrayList<>();
  private static Hero player;
  private float playerStartX = 50; // temp, the x coordinate of character.
  private float groundHeight = 20; // temp, the height of the ground.
  private float playerStartY = groundHeight;
  private int difficulty = 0; // default difficulty
  private int dayOrNight = 0; // default background
  private float maxframesize = 1800;
  private float minframesize = -50;
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
    Monster m1 = (Monster) builder.createObject("Monster");
    m1.setPosition(800, groundHeight);
    m1.setStrength(0);
    m1.setSpeed(10);
    Monster m2 = (Monster) builder.createObject("Monster");
    m2.setPosition(1250, groundHeight);
    m2.setStrength(0);
    m2.setSpeed(20);
    Monster m3 = (Monster) builder.createObject("Monster");
    m3.setPosition(1750, groundHeight);
    m3.setStrength(0);
    m3.setSpeed(30);
    Coin c1 = (Coin) builder.createObject("Coin");
    c1.setPosition(1000, groundHeight);
    Coin c2 = (Coin) builder.createObject("Coin");
    c2.setPosition(1500, groundHeight);
    Coin c3 = (Coin) builder.createObject("Coin");
    c3.setPosition(1750, groundHeight);
    Objects.add(player);
    Objects.add(m1);
    Objects.add(m2);
    Objects.add(m3);
    Objects.add(c1);
    Objects.add(c2);
    Objects.add(c3);
    if (this.difficulty == 0) {
      m2.setStates(false);
      m3.setStates(false);
    } else if (this.difficulty == 1) {
      m3.setStates(false);
    }
  }

  public void setmaxFramesize(float x) {
    this.maxframesize = x;
  }

  public void setMinframesize(float x) {
    this.minframesize = x;
  }

  public void rightButtonPress() {
    if (player.getX() < this.maxframesize) {
      player.moveRight();
    }
    update();
  }

  public void leftButtonPress() {
    if (player.getX() > this.minframesize) {
      player.moveLeft();
    }
    update();
  }

  public void attackButtonPress() {
    player.attack();
    update();
    player.notAttack();
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
}
