package com.example.phase1.Controls;

import com.example.phase1.Animations.IAnimator;

public interface IController {
    void moveLeft(IAnimator animator);
    void moveRight(IAnimator animator);
    void attack(IAnimator animator);
}
