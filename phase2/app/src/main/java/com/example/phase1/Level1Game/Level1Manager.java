package com.example.phase1.Level1Game;

import com.example.phase1.BackendStorage.GameManager;
import com.example.phase1.Objects.Coin;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Hero;
import com.example.phase1.Objects.Monster;
import com.example.phase1.Objects.ObjectBuilder;

import java.util.List;
import java.util.ArrayList;

public class Level1Manager {
  ArrayList<GameObject> Objects = new ArrayList<>();
  private static Hero player;
  private float playerStartX = 50; // temp, the x coordinate of character.
  private float groundHeight = 20; // temp, the height of the ground.
  private float playerStartY = groundHeight;
  private int difficulty = 0; // default difficulty
  private int dayOrNight = 0;//default background

  public Level1Manager() {
    ObjectBuilder builder = new ObjectBuilder(this.difficulty);
    player = (Hero) builder.createObject("Hero");
    player.setPosition(playerStartX, playerStartY);
    Monster m1 = (Monster) builder.createObject("Monster");
    m1.setPosition(800, groundHeight);
    m1.setStrength(0);
    Coin c1 = (Coin) builder.createObject("Coin");
    c1.setPosition(1000, groundHeight);
    Coin c2 = (Coin) builder.createObject("Coin");
    c2.setPosition(1500, groundHeight);
    Coin c3 = (Coin) builder.createObject("Coin");
    c3.setPosition(1750, groundHeight);
    Objects.add(player);
    Objects.add(m1);
    Objects.add(c1);
    Objects.add(c2);
    Objects.add(c3);

  }

  public void rightButtomPress() {
    player.moveRight();
    update();
  }

  public void leftButtomPress() {
    player.moveLeft();
    update();
  }

  public void attackButtomPress() {
    player.attack();
    update();
    player.notAttack();
  }

  public void jumpButtomPress() {
  }

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

  public void setDifficulty(int difficulty){this.difficulty = difficulty;}
  public void setDayOrNight(int dayOrNight){this.dayOrNight = dayOrNight;}
  public ArrayList getObjects(){return this.Objects;}
}
