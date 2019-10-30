package com.example.phase1;

import com.example.phase1.BackendStorage.GameManager;

import java.util.ArrayList;
import java.util.List;

public class Level2Manager extends GameManager {
  public static final int GROUND_HEIGHT = 0;
  List<Obstacle> Obstacles = new ArrayList<>();
  private static Hero player;
  private int playerStartX = 138;
  private int playerScore = 0;
  private int playerStartY = GROUND_HEIGHT;
  private int playerHealth = 4;

  public Level2Manager() {
    player = new Hero(playerStartX, GROUND_HEIGHT);
    player.setStates(true);

    Obstacle rock1 = new Obstacle(185, GROUND_HEIGHT);
    Obstacle rock2 = new Obstacle(433, GROUND_HEIGHT);
    Obstacle rock3 = new Obstacle(638, GROUND_HEIGHT);

    Obstacles.add(rock1);
    Obstacles.add(rock2);
    Obstacles.add(rock3);
  }

  //  public void setObstacleSpeed(int speed) {
  //    for (Obstacle obstacle: Obstacles) {
  //      obstacle.setSpeed(speed);
  //    }
  //  }

  public void update() {
    for (Obstacle obstacle : Obstacles) {
      obstacle.update();
      if (obstacle.getX() <= this.playerStartX && !(obstacle.getPassed())) {
        obstacle.setPassed(true);

        if (playerStartY == 1) {
          this.playerScore += 100;
        } else {
          this.playerHealth -= 1;
          //          deductHealth(1);
        }
      }
    }
  }

  @Override
  public int getScore() {
    return this.playerScore;
  }

  @Override
  public int getHealth() {
    return this.playerHealth;
  }

  void setHealth(int health) {
    this.playerHealth = health + 1;
  }

  void playerJump(boolean jump) {
    if (jump) {
      this.playerStartY = 1;
    } else {
      this.playerStartY = 0;
    }
  }
}
