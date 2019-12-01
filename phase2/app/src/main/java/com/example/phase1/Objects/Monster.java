package com.example.phase1.Objects;

import java.util.Random;

public class Monster extends Character {
  private Hero player;
  private int worth = 100;
  // temp, how many point the monster worth
  // (how many point the player could get when they kill this monster)
  private boolean heroHadGotPoint = false;
  // track is the player had already get the point from this monster or not;
  private boolean tracingHero = true;
  // Is the Monster tracingHero or move randomly
  private Random random;
  private int maxMoveLength = 15;
  private int moveLength = 0;
  // How many the monster will move (for moveRandomly)
  private int currentLength = 0;
  // how many does Monster moved already (for moveRandomly)

  public Monster() {
    super();
    random = new Random();
    this.setSpeed(10);
  }

  public void setPlayer(Hero player) {
    this.player = player;
  }

  public void update() {
    if (player.isAttack()) { // if player isAttack
      if (isGetHit() && getStates()) { // if player hit the monster
        damaged(player.getStrength());
      }
    }
    super.update();
    if ((!heroHadGotPoint) && (!getStates())) {
      player.addScore(this.worth);
      heroHadGotPoint = true;
    }
    if (tracingHero == true) traceHero();
    else moveRandomly();
    if (isTouchHero() && getStates()) {
      player.damaged(getStrength());
    }
  }

  public boolean isGetHit() { // Check is the monster is get hit by Hero or not
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

    return (isTouchHero() && player.getStates());
  }

  public boolean isMoveLeft() {
    if (tracingHero) {
      if (player.getX() < this.x) return true;
    } else {
      if (currentLength > moveLength) return true;
    }
    return false;
  }

  public void setWorth(int worth) {
    this.worth = worth;
  }

  public void setTracingHero(boolean isTracing) {
    this.tracingHero = isTracing;
  }

  private void moveRandomly() {
    if (moveLength == currentLength) {
      moveLength = random.nextInt(maxMoveLength * 2 + 1) - maxMoveLength;
      currentLength = 0;
    } else {
      if (moveLength > currentLength) {
        currentLength++;
        moveRight();
      } else {
        currentLength--;
        moveLeft();
      }
    }
  }
}
