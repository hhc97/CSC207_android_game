package com.example.phase2.Level3Game;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class DisplayHandler {
    private static Button[] buttons = new Button[4];
    @SuppressLint("StaticFieldLeak")
    private static TextView out;

    DisplayHandler(Button[] b, TextView o){
        buttons = b;
        out = o;
    }

    @SuppressLint("SetTextI18n")
    void startSequence(){
        disableButtons(); // disable the buttons while the sequence is displaying
        setButtonsInvisible();
        out.setText("Wait for the sequence to display");
        out.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    void endSequence(){
        enableButtons(); // enable buttons after sequence is displayed
        out.setText("Start!");
        out.setVisibility(View.VISIBLE);
    }

    void setButtonVisible(int i){
        buttons[i].setVisibility(View.VISIBLE);
    }

    /** Set all Button.Enabled and Clickable properties to true */
    static void enableButtons(){
        for (Button button : buttons) {
            button.setEnabled(true);
            button.setClickable(true);
        }
    }

    static void disableButtons(){
        for (Button button : buttons) {
            button.setEnabled(false);
            button.setClickable(false);
        }
    }

    static void setButtonsVisible(){
        for (Button button : buttons) {
            button.setVisibility(View.VISIBLE);
        }
    }

    static void setButtonsInvisible(){
        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
        }
    }

    static void setText(String s){
        out.setText(s);
        out.setVisibility(View.VISIBLE);
    }

    static Button getButton(int i){
        return buttons[i];
    }

}
