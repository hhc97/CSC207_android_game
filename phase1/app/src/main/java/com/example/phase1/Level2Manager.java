package com.example.phase1;

import java.util.ArrayList;
import java.util.List;

public class Level2Manager {
  private float dpHeight;
  private float dpWidth;
  private int groundHeight = 0;
  List<Obstacle> Obstacles = new ArrayList<>();
  private static Hero player;
  private int playerStartX = 138;
  private int playerScore = 0;

  public Level2Manager() {
    player = new Hero(playerStartX, groundHeight);
    player.setStates(true);

    Obstacle rock1 = new Obstacle(185, groundHeight);
    Obstacle rock2 = new Obstacle(433, groundHeight);
    Obstacle rock3 = new Obstacle(638, groundHeight);

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
        this.playerScore += 100;
      }
    }
  }

  public int getScore() {
    return this.playerScore;
  }
}
