package com.example.phase1;

import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Level2Manager {
  private float dpHeight;
  private float dpWidth;
  private int groundHeight = 0;
  List<GameObject> Obstacles = new ArrayList<>();
  private static Hero player;
  private int playerStartX = 140;
  private int playerStartY = groundHeight;

  public Level2Manager(View context) {
    player = new Hero(playerStartX, playerStartY);
    player.setStates(true);

    GameObject rock1 = new GameObject(182, groundHeight);
    GameObject rock2 = new GameObject(430, groundHeight);
    GameObject rock3 = new GameObject(640, groundHeight);

    Obstacles.add(rock1);
    Obstacles.add(rock2);
    Obstacles.add(rock3);

    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    dpHeight = displayMetrics.heightPixels / displayMetrics.density;
    dpWidth = displayMetrics.widthPixels / displayMetrics.density;
  }

  public void update() {
    for (GameObject obstacle : Obstacles) {
      obstacle.update();
      if (obstacle.getY() == player.getY()) {
        player.update();
      }
    }
  }
}
