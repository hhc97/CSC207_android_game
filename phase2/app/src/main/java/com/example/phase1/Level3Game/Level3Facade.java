package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;

import java.util.ArrayList;

class Level3Facade {
    private Level3Manager Level3;
    @SuppressLint("StaticFieldLeak")
    private static DisplayHandler displayHandler;

    static void setAttempts(int i) {
        Level3Manager.attempts = 0;
    }

    static void setButtonVisible(int i) {
        displayHandler.setButtonVisibile(i);
    }

    void setDisplayHandler(DisplayHandler displayHandler) {
        Level3Facade.displayHandler = displayHandler;
    }
    static int getAttempts(){
        return Level3Manager.attempts;
    }
    void setLevel3(Level3Manager level3) {
        Level3 = level3;
    }
    static void startSequence(){
        displayHandler.startSequence();
    }
    static void endSequence(){
        displayHandler.endSequence();
    }
    static void disable_buttons(){
        DisplayHandler.set_buttons_invisible();
    }
    static void set_text(String s){
        DisplayHandler.set_text(s);
    }
    static ArrayList<Integer> getSequence(){
        return Level3Manager.getSequence();
    }
    static void setUserInput(int pressed){
        Level3Manager.setUserInput(pressed);
    }
    static void clearInput(){
        Level3Manager.clearInput();
    }
    static int checkConditions(){
        return Level3Manager.checkConditions();
    }
}
