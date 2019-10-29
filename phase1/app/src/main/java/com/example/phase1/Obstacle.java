package com.example.phase1;

public class Obstacle extends GameObject {

  public Obstacle(float x, float y) {
    super(x, y);
  }

  @Override
  public void update() {
    this.x -= 78.5;

    if (this.x <= 0) {
      this.x = 785;
    }
  }
}
