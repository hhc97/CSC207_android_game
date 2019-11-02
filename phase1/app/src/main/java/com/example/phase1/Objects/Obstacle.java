package com.example.phase1.Objects;

public class Obstacle extends GameObject {
  private boolean hasPassed = false;

  public Obstacle(float x, float y) {
    super(x, y);
  }

  @Override
  public void update() {
    this.x -= 6.28;

    if (this.x <= 0) {
      this.x = 785;
      this.hasPassed = false;
    }
  }

  public void setPassed(boolean pass) {
    this.hasPassed = pass;
  }

  public boolean getPassed() {
    return this.hasPassed;
  }
}
