package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class DisplayHandler {
    private Button[] buttons = new Button[4];
    @SuppressLint("StaticFieldLeak")
    private TextView out;

    DisplayHandler(Button[] b, TextView o){
        buttons = b;
        out = o;
    }

    @SuppressLint("SetTextI18n")
    void startSequence(){
        disableButtons(); // disable the buttons while the sequence is displaying
        setButtonsVisible();
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

    void setButtonInvisible(int i){
        buttons[i].setVisibility(View.INVISIBLE);
    }

    /** Set all Button.Enabled and Clickable properties to true */
    void enableButtons(){
        for (Button button : buttons) {
            button.setEnabled(true);
            button.setClickable(true);
        }
    }

    void disableButtons(){
        for (Button button : buttons) {
            button.setEnabled(false);
            button.setClickable(false);
        }
    }

    void setButtonsVisible(){
        for (Button button : buttons) {
            button.setVisibility(View.VISIBLE);
        }
    }

    void setButtonsInvisible(){
        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
        }
    }

    void setText(String s){
        out.setText(s);
        out.setVisibility(View.VISIBLE);
    }

    Button getButton(int i){
        return buttons[i];
    }

}
