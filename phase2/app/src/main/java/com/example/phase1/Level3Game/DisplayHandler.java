package com.example.phase1.Level3Game;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

class DisplayHandler {
    private Button[] buttons;
    @SuppressLint("StaticFieldLeak")
    private TextView out;
    private TextView keyText;
    private Button keyButton;

    DisplayHandler(Button[] b, TextView o, TextView k, Button keyButton){
        buttons = b;
        out = o;
        keyText = k;
        this.keyButton = keyButton;
    }

    void hideKeyText(){keyText.setVisibility(INVISIBLE);}

    void showKeyText(){keyText.setVisibility(VISIBLE);}

    void showKeyButton(){keyButton.setVisibility(VISIBLE);}

    void hideKeyButton(){keyButton.setVisibility(INVISIBLE);}

    void enableKeyButton(){
        keyButton.setEnabled(true);
        keyButton.setClickable(true);
    }

    void disableKeyButton(){
        keyButton.setEnabled(false);
        keyButton.setClickable(false);
    }

    @SuppressLint("SetTextI18n")
    void startSequence(){
        disableButtons(); // disable the buttons while the sequence is displaying
        disableKeyButton();
        hideKeyButton();
        hideKeyText();
        setButtonsVisible();
        out.setText("Wait for the sequence to display");
        out.setVisibility(VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    void endSequence(){
        out.setText("Start!");
        out.setVisibility(VISIBLE);
        enableButtons(); // enable buttons after sequence is displayed
    }

    void setButtonVisible(int i){
        buttons[i].setVisibility(VISIBLE);
    }

    void setButtonInvisible(int i){
        buttons[i].setVisibility(INVISIBLE);
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
            button.setVisibility(VISIBLE);
        }
    }

    void setButtonsInvisible(){
        for (Button button : buttons) {
            button.setVisibility(INVISIBLE);
        }
    }

    void setText(String s){
        out.setText(s);
        out.setVisibility(VISIBLE);
    }

    Button getButton(int i){
        return buttons[i];
    }

}
