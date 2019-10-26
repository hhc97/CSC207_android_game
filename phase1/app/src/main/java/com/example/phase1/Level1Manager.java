package com.example.phase1;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;

public class Level1Manager {
    List<Monster> Monsters = new ArrayList();
    static Hero player;
    private boolean isRun;
    private int playerStartX = 0;// temp, the x coordinate of character
    private int groundHeight = 20;// temp, the height of the ground
    private int playerStartY = groundHeight;

    public Level1Manager(){
        player = new Hero(playerStartX, playerStartY);
        isRun = true;
    }
    public void draw(){}
    public void update(){}
}
