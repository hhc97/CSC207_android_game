package com.example.phase1;

public class Obstacle extends GameObject {

  public Obstacle(int x, int y) {
    super(x, y);
  }

  @Override
  public void update(){
    this.x -= 1;

    if (this.x <= 0) {
      this.x = 100;
    }
  }
}
