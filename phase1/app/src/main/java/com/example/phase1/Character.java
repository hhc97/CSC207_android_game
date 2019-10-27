package com.example.phase1;

public class Character extends GameObject {

  boolean isAlive;
  protected int speed;

  public Character(int x, int y) { // NEED GameManager.getHealth()
    super(x, y);
    this.isAlive = true;
    this.speed = 1; // temp value of speed
  }

  public void updateHealth() {}

  public void moveRight() {
    x += speed;
  }

  public void moveLeft() {
    this.x -= speed;
  }

  public void update() { // update image
  }

  public void die() {}
}
