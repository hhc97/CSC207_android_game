package com.example.phase1.Level2Game;

import com.example.phase1.Objects.Hero;
import com.example.phase1.Objects.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class Level2Manager {
  private static final int GROUND_HEIGHT = 0;
  private List<Obstacle> Obstacles = new ArrayList<>();
  private int playerStartX = 138;
  private int playerStartY = GROUND_HEIGHT;
  private Level2Activity parent;

  Level2Manager() {
    Hero player = new Hero(playerStartX, GROUND_HEIGHT);
    player.setStates(true);

    Obstacle rock1 = new Obstacle(185, GROUND_HEIGHT);
    Obstacle rock2 = new Obstacle(433, GROUND_HEIGHT);
    Obstacle rock3 = new Obstacle(638, GROUND_HEIGHT);

    Obstacles.add(rock1);
    Obstacles.add(rock2);
    Obstacles.add(rock3);
  }

  void setParent(Level2Activity p) {
    parent = p;
  }

  // Updates player's health and score based on interactions with obstacles.
  public void update() {
    for (Obstacle obstacle : Obstacles) {
      obstacle.update();
      if (obstacle.getX() <= this.playerStartX && !(obstacle.getPassed())) {
        obstacle.setPassed(true);

        if (playerStartY == 1) {
          parent.addScore(100);
        } else {
          parent.deductHealth(1);
        }
      }
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
}
