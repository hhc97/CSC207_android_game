package com.example.phase1;

import androidx.constraintlayout.solver.widgets.WidgetContainer;

public class Monster extends Character {
  private int damage = 5; // temp, how many damage can monster deal to the Hero.
  private Hero player;

  public Monster(int x, int y) {
    super(x, y);
    this.player = Level1Manager.player;
    this.setSpeed(30);
  }

  public void update() {
    if (player.isAttack()) { // if player isAttack
      if (isGetHit()) { // if player hit the monster
        damaged(player.getStrength());
      }
    }
    super.update();
    traceHero();
    if (isTouchHero()) {
      player.damaged(getStrength());
    }
  }

  private boolean isGetHit() { // Check is the monster is get hit by Hero or not
    int[] xRange = player.getAttackRangeX(); // get the x coordinate range of AttackRange
    int[] yRange = player.getAttackRangeY(); // get y coordinate range of AttackRange
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

  private void traceHero() {
    int playerX = player.getX();
    if (playerX < this.x) {
      moveLeft();
    }
    if (playerX > this.x) {
      moveRight();
    }
  }

  private boolean isTouchHero() {
    int[] xRange = {player.getX(), player.getX() + player.WIDTH};
    int[] yRange = {player.getY(), player.getY() + player.HEIGHT};
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
}
