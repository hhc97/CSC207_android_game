package com.example.phase1;

import java.util.ArrayList;
import java.util.List;

public class Level2Manager {
  private int groundHeight = 0;
  List<GameObject> Obstacles = new ArrayList<>();
  static Hero player;
  private int playerStartX = 0;
  private int playerStartY = groundHeight;

  public Level2Manager() {
    player = new Hero(playerStartX, playerStartY);
    player.setAlive(true);
    GameObject rock1 = new GameObject(50, groundHeight);
    GameObject rock2 = new GameObject(80, groundHeight);
    GameObject rock3 = new GameObject(120, groundHeight);
    Obstacles.add(rock1);
    Obstacles.add(rock2);
    Obstacles.add(rock3);
  }

  public void update() {
    player.update();
    for (GameObject obstacle: Obstacles) {
      obstacle.update();
    }
  }
}
