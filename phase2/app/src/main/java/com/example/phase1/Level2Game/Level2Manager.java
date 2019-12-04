package com.example.phase1.Level2Game;

import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Hero;
import com.example.phase1.Objects.ObjectBuilder;
import com.example.phase1.Objects.Obstacle;
import com.example.phase1.Objects.Coin;
import com.example.phase1.Objects.Potion;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

class Level2Manager {
  private static final float GROUND_HEIGHT = 0;
  private List<Obstacle> Obstacles = new ArrayList<>();
  private List<GameObject> gameObjects = new ArrayList<>();
  private float playerStartX = 138;
  private float playerStartY = GROUND_HEIGHT;
  private Level2Activity parent;
  private ObjectBuilder builder;
  private int difficulty;
  private Hero player;
  private boolean freebie = true;

  Level2Manager() {
    builder = new ObjectBuilder();
    Hero player = (Hero) builder.createObject("Hero");
    player.setPosition(playerStartX, playerStartY);
    this.player = player;

    Obstacle rock1 = (Obstacle) builder.createObject("Obstacle");
    rock1.setPosition(185, GROUND_HEIGHT);
    Obstacle rock2 = (Obstacle) builder.createObject("Obstacle");
    rock2.setPosition(433, GROUND_HEIGHT);
    Obstacle rock3 = (Obstacle) builder.createObject("Obstacle");
    rock3.setPosition(638, GROUND_HEIGHT);
    Coin c1 = (Coin) builder.createObject("Coin");
    c1.setPosition(1000, GROUND_HEIGHT);
    Coin c2 = (Coin) builder.createObject("Coin");
    c2.setPosition(1500, GROUND_HEIGHT);
    Coin c3 = (Coin) builder.createObject("Coin");
    c3.setPosition(1750, GROUND_HEIGHT);
    Potion p = (Potion) builder.createObject("Potion");
    p.setPosition(400, GROUND_HEIGHT);

    Obstacles.add(rock1);
    Obstacles.add(rock2);
    Obstacles.add(rock3);
    gameObjects.add(c1);
    gameObjects.add(c2);
    gameObjects.add(c3);
    gameObjects.add(p);
  }

  void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  void setParent(Level2Activity p) {
    parent = p;
  }

  private void updateHealth() {
    if (difficulty == 0) {
      parent.deductHealth(30);
    } else if (difficulty == 1) {
      parent.deductHealth(40);
    } else {
      parent.deductHealth(50);
    }
  }

  // Updates player's health and score based on interactions with obstacles.
  public void update() {
    player.update();
    updateObstacles();
    updateGameObjects();
  }

  private void updateObstacles() {
    for (Obstacle obstacle : Obstacles) {
      obstacle.update();
      if (obstacle.getX() <= this.playerStartX && !(obstacle.getPassed())) {
        obstacle.setPassed(true);

        if (freebie) {
          freebie = false;
          continue;
        }

        if (playerStartY == 1) {
          parent.addScore(100);
        } else {
          updateHealth();
        }
      }
    }
  }

  private void updateGameObjects() {
    for (GameObject obj : gameObjects) {
      obj.update();
    }
  }

  // Checks if this player is currently jumping in the air.
  void playerJump(boolean jump) {
    if (jump) {
      this.playerStartY = 1;
    } else {
      this.playerStartY = 0;
    }
  }

  // Hero object move right
  public void rightAction() {
    player.moveRight();
  }

  // Hero object move left
  public void leftAction() {
    player.moveLeft();
  }

  public Hero getPlayer() {
    return this.player;
  }

  public List<GameObject> getGameObjects() {
    return gameObjects;
  }

  public void playerAlive() {
    player.setHealth(1);
    player.setStates(true);
  }

  public boolean checkIsWinning() {
    boolean isWon = true;
    for (GameObject obj : gameObjects) {
      if (obj.getStates()) isWon = false;
    }
    return isWon;
  }
}
