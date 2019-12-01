package com.example.phase2.Level2Game;

import com.example.phase2.Objects.Hero;
import com.example.phase2.Objects.ObjectBuilder;
import com.example.phase2.Objects.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class Level2Manager {
  private static final float GROUND_HEIGHT = 0;
  private List<Obstacle> Obstacles = new ArrayList<>();
  private float playerStartX = 138;
  private float playerStartY = GROUND_HEIGHT;
  private Level2Activity parent;
  private ObjectBuilder builder;
  private int difficulty;

  Level2Manager() {
    builder = new ObjectBuilder();
    Hero player = (Hero) builder.createObject("Hero");
    player.setPosition(playerStartX, playerStartY);

    Obstacle rock1 = (Obstacle) builder.createObject("Obstacle");
    rock1.setPosition(185, GROUND_HEIGHT);
    Obstacle rock2 = (Obstacle) builder.createObject("Obstacle");
    rock2.setPosition(433, GROUND_HEIGHT);
    Obstacle rock3 = (Obstacle) builder.createObject("Obstacle");
    rock3.setPosition(638, GROUND_HEIGHT);

    Obstacles.add(rock1);
    Obstacles.add(rock2);
    Obstacles.add(rock3);
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
    }
    else if (difficulty ==1) {
      parent.deductHealth(40);
    }
    else {
      parent.deductHealth(50);
    }
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
          updateHealth();
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
