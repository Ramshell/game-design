package com.platformer.ar;

import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class ScreenDispatcher implements IScreenDispatcher {

    public ArrayList<Screen> screens;
    private boolean isCurrenScreenEnded = false;
    private int currentIndex = 0;

    ScreenDispatcher(){
        screens = new ArrayList<Screen>();
    }

    public void addScreen(Screen screen){
        screens.add(screen);
    }


    @Override
    public void endCurrentScreen() {
        isCurrenScreenEnded = true;
    }

    @Override
    public Screen getNextScreen() {
        if(isCurrenScreenEnded){
            isCurrenScreenEnded = false;
            currentIndex++;
        }

        if(screens.size() > currentIndex){
            return screens.get(currentIndex);
        }else{
            return screens.get(0);
        }
    }

    public void win(Screen screen){
        screens.clear();
        isCurrenScreenEnded = true;
        screens.add(screen);
    }
}
