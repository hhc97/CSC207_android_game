package com.example.phase1;

public class Character extends GameObject {

    protected int speed;// how fast the character moves
    private int strength;// how strong is the character, how many damage it can deal
    private int health;


    public Character(int x, int y) {  //NEED GameManager.getHealth()
        super(x, y);
        this.speed = 1; //temp value of speed
        this.strength = 1;// temp
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
    public void die(){ this.states = false; }
    public int getStrength(){return this.strength;}
    public void damaged (int damage){
        this.health = this.health - damage;
    }
    public void heal(int value){this.health = this.health+value;}
}
