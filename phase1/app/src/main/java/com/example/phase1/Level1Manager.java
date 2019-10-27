package com.example.phase1;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;

public class Level1Manager {
    List<GameObject> Objects = new ArrayList();
    static Hero player;
    private int playerStartX = 50;// temp, the x coordinate of character
    private int groundHeight = 20;// temp, the height of the ground
    private int playerStartY = groundHeight;

    public Level1Manager(){
        player = new Hero(playerStartX, playerStartY);
        Objects.add(new Monster(20, groundHeight));
        Objects.add(new Monster(35, groundHeight));
        Objects.add(new Coin(15, groundHeight));

    }
    public void update(){
        player.update();
        for(GameObject obj : Objects){
            obj.update();
        }
        player.notAttack();
    }
    public void onLoop(){
        while(player.getStates()){
            update();
            removeDisappearedObjects();
        }
    }
    private void removeDisappearedObjects(){
        for (GameObject obj : Objects){
            if (!obj.getStates()){
                Objects.remove(obj);
            }
        }
    }
    public int heroMoveLeft(){
        player.moveLeft();
        return player.getX();
    }
    public int heroMoveRight(){
        player.moveRight();
        return player.getX();
    }
}
