package com.example.phase1;

public class Potion extends GameObject {
  private int value; // How much health does Hero heal when drinking this potion
  private Hero player;

  public Potion(int x, int y) {
    super(x, y);
    this.value = 5; // temp
  }

  @Override
  public void update() {
    if (isTouchHero()) {
      player.heal(value);
      setStates(false);
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
