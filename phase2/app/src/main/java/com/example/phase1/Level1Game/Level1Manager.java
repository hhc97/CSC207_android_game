package com.example.phase1.Level1Game;

import com.example.phase1.Objects.Coin;
import com.example.phase1.Objects.GameObject;
import com.example.phase1.Objects.Hero;
import com.example.phase1.Objects.Monster;

import java.util.List;
import java.util.ArrayList;

public class Level1Manager {
  List<GameObject> Objects = new ArrayList<>();
  private static Hero player;
  private float playerStartX = 50; // temp, the x coordinate of character.
  private float groundHeight = 20; // temp, the height of the ground.
  private float playerStartY = groundHeight;

  public Level1Manager() {
    player = new Hero(playerStartX, playerStartY);
    Objects.add(new Monster(800, groundHeight));
    ((com.example.phase1.Objects.Character) Objects.get(0)).setStrength(0);
    Objects.add(new Coin(1000, groundHeight));
    Objects.add(new Coin(1500, groundHeight));
    Objects.add(new Coin(1750, groundHeight));
  }

  // Update every object in the array list.
  public void update() {
    player.update();
    // Update every object in the array list.
    for (GameObject obj : Objects) {
      obj.update();
    }
  }

  private void removeDisappearedObjects() {
    for (GameObject obj : Objects) {
      if (!obj.getStates()) {
        Objects.remove(obj);
      }
    }
  }

  // Update hero's x Coordinate and move left.
  public float heroMoveLeft() {
    player.moveLeft();
    return player.getX();
  }

  // Update hero's x Coordinate and move right.
  public float heroMoveRight() {
    player.moveRight();
    return player.getX();
  }

  public static Hero getPlayer() {
    return player;
  }
}
