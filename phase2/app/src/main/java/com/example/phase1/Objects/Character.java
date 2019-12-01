package com.example.phase1.Objects;

public class Character extends GameObject {

  private int speed;
  // how fast the character moves
  private int strength;
  // how strong is the character, how many damage it can deal
  private int health;

  public Character() { // NEED GameManager.getHealth()
    super();
    this.speed = 50; // temp value of speed
    this.strength = 1; // temp
    this.health = 1; // default
  }

  public void moveRight() {
    if (this.x < maxFrameSize) this.x += speed;
  }

  public void moveLeft() {
    if (this.x > minFrameSize) this.x -= 10 + speed;
  }

  public void update() { // if character's health is 0, then die
    if (this.health <= 0) this.health = 0;
    if (this.health <= 0) die();
  }

  public void die() {
    this.states = false;
  }

  public int getStrength() {
    return this.strength;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public void damaged(int damage) {
    if (this.health - damage <= 0) this.health = 0;
    else this.health = this.health - damage;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getHealth() {
    return this.health;
  }
}
