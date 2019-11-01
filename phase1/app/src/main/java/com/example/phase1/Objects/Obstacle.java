package com.example.phase1.Objects;

public class Obstacle extends GameObject {
  private int speed;
  private boolean hasPassed = false;

  public Obstacle(float x, float y) {
    super(x, y);
    speed = 0;
  }

  @Override
  public void update() {
    this.x -= 6.28;

    if (this.x <= 0) {
      this.x = 785;
      this.hasPassed = false;
    }
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public void setPassed(boolean pass) {
    this.hasPassed = pass;
  }

  public boolean getPassed() {
    return this.hasPassed;
  }
}
