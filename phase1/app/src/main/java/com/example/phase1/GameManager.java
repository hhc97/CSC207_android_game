package com.example.phase1;

import androidx.appcompat.app.AppCompatActivity;

abstract class GameManager extends AppCompatActivity {
  private int score = 0;

  int getScore() {
    return score;
  }

  void setScore(int s) {
    score = s;
  }
}
