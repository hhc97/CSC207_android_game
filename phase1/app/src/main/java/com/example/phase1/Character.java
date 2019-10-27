package com.example.phase1;

public class Character extends GameObject {

    private boolean charStates;// Alive or dead
    protected int speed;// how fast the character moves
    private int strenght;// how strong is the character, how many damage it can deal
    private int health;


    public Character(int x, int y) {  //NEED GameManager.getHealth()
        super(x, y);
        this.charStates = true;
        this.speed = 1; //temp value of speed
        this.strenght = 1;// temp
        this.health = 1;// default
    }

    public void updateHealth(){//might be use when update and save the health
    }

    public void moveRight(){
        this.x += speed;
    }
    public void moveLeft(){
        this.x -= speed;
    }

    public void update(){  //if character's health is 0, then die
        if (this.health <= 0)
            die();
    }
    public void die(){ this.charStates = false; }
    public boolean isAlive(){return this.charStates;}
    public int getStrenght(){return this.strenght;}
    public void setAlive(boolean charStates) {this.charStates = charStates;}
    public void damaged (int damage){
        this.health = this.health - damage;
    }
}
