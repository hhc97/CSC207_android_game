package com.example.phase1;

public class Coin extends GameObject {
    private Hero player;

    public Coin(float x, float y){
        super(x, y);
        this.player = Level1Manager.player;
    }

    public void update(){
        if (isTouchHero()){
            player.addCoins();
            setStates(false);
        }
    }

    private boolean isTouchHero() {
        float[] xRange= {player.getX(), player.getX()+player.WIDTH};
        float[] yRange= {player.getY(), player.getY()+player.HEIGHT};
        if ((x <= xRange[0] && xRange[0] <= (x + WIDTH))// Case 1, the Monster is bigger than attack range
                || (x <= xRange[1] && xRange[1] <= (x + WIDTH))) {
            if ((y <= yRange[0] && yRange[0] <= (y + HEIGHT))
                    || (y <= yRange[1] && yRange[1] <= (y + HEIGHT))) {
                return true;
            }
        }
        if ((xRange[0] <= x && (x + WIDTH) <= xRange[0])// Case 2, the Monster is smaller than attack range
                || (xRange[1] <= x && (x + WIDTH) <= xRange[1])) {
            if ((yRange[0] <= y && (y + HEIGHT) <= yRange[0])
                    || (yRange[1] <= y && (y + HEIGHT) <= yRange[1])) {
                return true;
            }
        }
        return false;
    }
}
