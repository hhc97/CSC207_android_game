package com.example.phase1.Objects;

import android.graphics.Bitmap;

import pl.droidsonroids.gif.GifImageView;

public class GameObject {

  protected final int WIDTH = 100; // temp value
  protected final int HEIGHT = 100; // temp value
  protected float x; // the x coordinate value
  protected float y; // the y coordinate value
  protected boolean states;
  private GifImageView image;

  public GameObject(float x, float y) {
    this.x = x;
    this.y = y;
    this.states = true;
  }

  public float getX() { // getter for x coordinate
    return this.x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() { // getter for y coordinate
    return this.y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public int getHEIGHT() { // getter for the calculated Height value
    return this.HEIGHT;
  }

  public int getWIDTH() { // getter for the calculated width value
    return this.WIDTH;
  }

  public void setStates(boolean states) {
    this.states = states;
  }

  public boolean getStates() {
    return this.states;
  }

  public void update() {}

  public void setImage(GifImageView image) {
    this.image = image;
  }

  public GifImageView getImage() {
    return this.image;
  }
}
