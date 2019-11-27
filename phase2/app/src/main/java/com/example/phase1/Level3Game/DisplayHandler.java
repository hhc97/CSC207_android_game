package com.example.phase1.Level3Game;

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
        disable_buttons(); // disable the buttons while the sequence is displaying
        set_buttons_invisible();
        out.setText("Wait for the sequence to display");
        out.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    void endSequence(){
        enable_buttons(); // enable buttons after sequence is displayed
        out.setText("Start!");
        out.setVisibility(View.VISIBLE);
    }

    void setButtonVisibile(int i){
        buttons[i].setVisibility(View.VISIBLE);
    }

    /** Set all Button.Enabled and Clickable properties to true */
    private static void enable_buttons(){
        for (Button button : buttons) {
            button.setEnabled(true);
            button.setClickable(true);
        }
    }

    private void disable_buttons(){
        for (Button button : buttons) {
            button.setEnabled(false);
            button.setClickable(false);
        }
    }

    static void set_buttons_invisible(){
        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
        }
    }

    static void set_text(String s){
        out.setText(s);
        out.setVisibility(View.VISIBLE);
    }

    static Button getButton(int i){
        return buttons[i];
    }

}
