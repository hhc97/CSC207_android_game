package com.example.phase2.Controls;

import com.example.phase2.Animations.IAnimator;

public interface IController {
    void moveLeft(IAnimator animator);
    void moveRight(IAnimator animator);
    void attack(IAnimator animator);
}
