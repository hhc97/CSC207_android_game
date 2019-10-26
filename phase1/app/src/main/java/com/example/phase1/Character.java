package com.example.phase1;

public class Character extends GameObject {

    boolean isAlive;
    private int speed;


    public Character(int x, int y) {  //NEED GameManager.getHealth()
        super(x, y);
        this.isAlive = true;
        this.speed = 1; //temp value of speed
    }

    public void updateHealth(){

    }

    public void moveRight(){
        this.x += speed;
    }

    public void update(){  //update image
    }



}
