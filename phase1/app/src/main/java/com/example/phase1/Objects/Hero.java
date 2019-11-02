package com.example.phase1.Objects;

public class Hero extends Character {

  private boolean isAttack;
  private float[] attackRangeX = new float[2]; // (x, y) coordinate of top left of attack range
  private float[] attackRangeY = new float[2]; // (x, y) coordinate of bottom right of attack range
  private int attackRange = WIDTH; // temp
  private int coins = 0;
  private int score = 0;

  public Hero(float x, float y) {
    super(x, y);
    this.isAttack = false;
  }

  public void attack() {
    this.isAttack = true;
  }

  public boolean isAttack() {
    return isAttack;
  }

  public void notAttack() {
    this.isAttack = false;
  }

  public float[] getAttackRangeX() {
    return attackRangeX;
  }

  public float[] getAttackRangeY() {
    return attackRangeY;
  }

  @Override
  public void moveRight() {
    super.moveRight();
    attackRangeX[0] = x;
    attackRangeX[1] = x + attackRange;
    attackRangeY[0] = y;
    attackRangeY[1] = y + HEIGHT;
  }

  @Override
  public void moveLeft() {
    super.moveLeft();
    attackRangeX[0] = x - attackRange;
    attackRangeX[1] = x;
    attackRangeY[0] = y;
    attackRangeY[1] = y + HEIGHT;
  }

  public int getCoins() {
    return coins;
  }

  public void addCoins() {
    this.coins++;
  }

  public void setCoins(int coins) {
    this.coins = coins;
  }

  public void heal(int value) {}

  public void addScore(int score) {
    this.score += score;
  }

  public int getScore() {
    return this.score;
  }
}
