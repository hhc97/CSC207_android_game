package com.example.phase1;

import android.graphics.Bitmap;

import pl.droidsonroids.gif.GifImageView;

public class GameObject {

  protected final int WIDTH = 20; // temp value
  protected final int HEIGHT = 20; // temp value
  protected int x; // the x coordinate value
  protected int y; // the y coordinate value
  protected boolean states;
  private pl.droidsonroids.gif.GifImageView image;

  public GameObject(int x, int y) {
    this.x = x;
    this.y = y;
    this.states = true;
  }

  public int getX() { // getter for x coordinate
    return this.x;
  }

  public int getY() { // getter for y coordinate
    return this.y;
  }

  public int getHEIGHT() { // getter for the calculated Height value
    return this.HEIGHT;
  }

  public int getWIDTH() { // getter for the calculated width value
    return this.WIDTH;
  }

  public void setStates(boolean states) {this.states = states;}

  public boolean getStates(){return this.states;}

  public void update(){}

  public void setImage(GifImageView image){
    this.image = image;
  }
}
