package com.example.phase1;

import java.util.List;
import java.util.ArrayList;

public class Level1Manager {
  List<GameObject> Objects = new ArrayList();
  static Hero player;
  private float playerStartX = 50; // temp, the x coordinate of character
  private float groundHeight = 20; // temp, the height of the ground
  private float playerStartY = groundHeight;

  public Level1Manager() {
    player = new Hero(playerStartX, playerStartY);
    Objects.add(new Monster(800, groundHeight));
    Objects.add(new Coin(1000, groundHeight));
    Objects.add(new Coin(1500, groundHeight));
    Objects.add(new Coin(2000, groundHeight));
  }

  public void update() {
    player.update();
    for (GameObject obj : Objects) {
      obj.update();
    }
  }

  public void onLoop() {
    while (player.getStates()) {
      update();
      removeDisappearedObjects();
    }
  }

  private void removeDisappearedObjects() {
    for (GameObject obj : Objects) {
      if (!obj.getStates()) {
        Objects.remove(obj);
      }
    }
  }

  public float heroMoveLeft() {
    player.moveLeft();
    return player.getX();
  }

  public float heroMoveRight() {
    player.moveRight();
    return player.getX();
  }
}
