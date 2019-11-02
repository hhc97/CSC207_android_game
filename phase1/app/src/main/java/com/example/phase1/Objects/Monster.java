package com.example.phase1.Objects;

import com.example.phase1.Level1Game.Level1Manager;

public class Monster extends Character {
  private Hero player;
  private int worth = 100;
  // temp, how many point the monster worth
  // (how many point the player could get when they kill this monster)
  private boolean heroHadGotPoint = false;
  // track is the player had already get the point from this monster or not;

  public Monster(float x, float y) {
    super(x, y);
    this.player = Level1Manager.getPlayer();
    this.setSpeed(10);
  }

  public void update() {
    if (player.isAttack()) { // if player isAttack
      if (isGetHit()) { // if player hit the monster
        damaged(player.getStrength());
      }
    }
    super.update();
    if ((!heroHadGotPoint) && (!states)) {
      player.addScore(this.worth);
      heroHadGotPoint = true;
    }
    traceHero();
    if (isTouchHero()) {
      player.damaged(getStrength());
    }
  }

  private boolean isGetHit() { // Check is the monster is get hit by Hero or not
    float[] xRange = player.getAttackRangeX();
    // get the x coordinate range of AttackRange
    float[] yRange = player.getAttackRangeY();
    // get y coordinate range of AttackRange
    if ((x <= xRange[0] && xRange[0] <= (x + WIDTH))
        // Case 1, the Monster is bigger than attack range
        || (x <= xRange[1] && xRange[1] <= (x + WIDTH))) {
      if ((y <= yRange[0] && yRange[0] <= (y + HEIGHT))
          || (y <= yRange[1] && yRange[1] <= (y + HEIGHT))) {
        return true;
      }
    }
    if ((xRange[0] <= x && (x + WIDTH) <= xRange[0])
        // Case 2, the Monster is smaller than attack range
        || (xRange[1] <= x && (x + WIDTH) <= xRange[1])) {
      if ((yRange[0] <= y && (y + HEIGHT) <= yRange[0])
          || (yRange[1] <= y && (y + HEIGHT) <= yRange[1])) {
        return true;
      }
    }
    return false;
  }

  private void traceHero() {
    float playerX = player.getX();
    if (playerX < this.x) {
      moveLeft();
    }
    if (playerX > this.x) {
      moveRight();
    }
  }

  private boolean isTouchHero() {
    float[] xRange = {player.getX(), player.getX() + player.WIDTH};
    float[] yRange = {player.getY(), player.getY() + player.HEIGHT};
    if ((x <= xRange[0]
            && xRange[0] <= (x + WIDTH)) // Case 1, the Monster is bigger than attack range
        || (x <= xRange[1] && xRange[1] <= (x + WIDTH))) {
      if ((y <= yRange[0] && yRange[0] <= (y + HEIGHT))
          || (y <= yRange[1] && yRange[1] <= (y + HEIGHT))) {
        return true;
      }
    }
    if ((xRange[0] <= x
            && (x + WIDTH) <= xRange[0]) // Case 2, the Monster is smaller than attack range
        || (xRange[1] <= x && (x + WIDTH) <= xRange[1])) {
      if ((yRange[0] <= y && (y + HEIGHT) <= yRange[0])
          || (yRange[1] <= y && (y + HEIGHT) <= yRange[1])) {
        return true;
      }
    }
    return false;
  }

  public boolean isAttack() {
    return isTouchHero();
  }

  public boolean isMoveLeft() {
    if (player.getX() < this.x) return true;
    return false;
  }

  public void setWorth(int worth) {
    this.worth = worth;
  }
}
