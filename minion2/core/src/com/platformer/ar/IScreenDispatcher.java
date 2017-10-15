package com.platformer.ar;

import com.badlogic.gdx.Screen;

public interface IScreenDispatcher {
    void endCurrentScreen();
    Screen getNextScreen();
}
