package com.example.phase1;

import java.util.ArrayList;
import java.util.List;

public class Level2Manager {
  private float dpHeight;
  private float dpWidth;
  private int groundHeight = 0;
  List<GameObject> Obstacles = new ArrayList<>();
  private static Hero player;
  private int playerStartX = 140;
  private int playerScore = 0;

  public Level2Manager() {
    player = new Hero(playerStartX, groundHeight);
    player.setStates(true);

    Obstacle rock1 = new Obstacle(182, groundHeight);
    Obstacle rock2 = new Obstacle(430, groundHeight);
    Obstacle rock3 = new Obstacle(640, groundHeight);

    Obstacles.add(rock1);
    Obstacles.add(rock2);
    Obstacles.add(rock3);
  }

  public void update() {
    for (GameObject obstacle : Obstacles) {
      obstacle.update();
      if (obstacle.getX() <= this.playerStartX) {
        this.playerScore += 100;
      }
    }
  }

  public int getScore() {
    return playerScore;
  }
}
