package com.example.phase1.Objects;

public class Hero extends Character {

  private boolean isAttack;
  private float[] attackRangeX = new float[2]; // (x, y) coordinate of top left of attack range
  private float[] attackRangeY = new float[2]; // (x, y) coordinate of bottom right of attack range
  private int attackRange = WIDTH; // temp
  private int coins = 0;
  private int score = 0;
  private int potion = 0;
  private int healthAfterPotion = 100;

  public Hero() {
    super();
    this.isAttack = false;
  }

  public void attack() {
    this.isAttack = true;
  }

  public boolean isAttack() {
    return isAttack;
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

  public void notAttack() {
    this.isAttack = false;
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

  public int getPotion() {
    return this.potion;
  }

  public void setPotion(int extraHealth) {
    this.potion = extraHealth;
  }

  public void addPotion() {
    this.potion++;
  }

  public void addScore(int score) {
    this.score += score;
  }

  public int getScore() {
    return this.score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void usePotion() {
    if (potion > 0) {
      potion--;
      setStates(true);
      setHealth(healthAfterPotion);
    }
  }
}
