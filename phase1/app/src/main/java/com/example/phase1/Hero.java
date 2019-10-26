package com.example.phase1;

import android.util.Pair;

import java.util.List;

public class Hero extends Character {

    private boolean isAttack;
    private int[] attackRangeX = new int[2];//(x, y) coordinate of top left of attack range
    private int[] attackRangeY = new int[2];//(x, y) coordinate of bottom right of attack range
    private int attackRange = WIDTH;//temp

    public Hero (int x, int y){
        super(x, y);
        this.isAttack = false;
    }
    public void attack(){
        isAttack = true;
    }
    public boolean isAttack(){
        return isAttack;
    }

    public int[] getAttackRangeX(){
        return attackRangeX;
    }
    public int[] getAttackRangeY(){
        return attackRangeY;
    }
    @Override
    public void moveRight(){
        x += speed;
        attackRangeX[0] = x;
        attackRangeX[1] = x+attackRange;
        attackRangeY[0] = y;
        attackRangeY[1] = y+HEIGHT;
    }
    @Override
    public void moveLeft(){
        x -= speed;
        attackRangeX[0] = x-attackRange;
        attackRangeX[1] = x;
        attackRangeY[0] = y;
        attackRangeY[1] = y+HEIGHT;
    }
}
