package com.example.phase1;

import androidx.constraintlayout.solver.widgets.WidgetContainer;

public class Monster extends Character {
  private int damage = 5; // temp, how many damage can monster dill to the Hero.

  public Monster(int x, int y) {
    super(x, y);
  }

  @Override
  public void update() {
    if (Level1Manager.player.isAttack()) { // if player isAttack
      if (isGetHit()) { // if player hit the monster
        die();
      }
    }
  }

  private boolean isGetHit() { // Check is the monster is get hit by Hero or not
    int[] xRange =
        Level1Manager.player.getAttackRangeX(); // get the x coordinate range of AttackRange
    int[] yRange = Level1Manager.player.getAttackRangeY(); // get y coordinate range of AttackRange
    if ((x <= xRange[0] && xRange[0] <= (x + WIDTH))
        || (x <= xRange[1] && xRange[1] <= (x + WIDTH))) {
      if ((y <= yRange[0] && yRange[0] <= (y + HEIGHT))
          || (y <= yRange[1] && yRange[1] <= (y + HEIGHT))) {
        return true;
      }
    }
    return false;
  }
}
