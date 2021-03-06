package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component{
    public int state = 0;
    public float time = 0.0f;

    public int get() {
        return state;
    }

    public void set(int newState) {
        state = newState;
    }

    public void change(int newState) {
        state = newState;
        time = 0.0f;
    }
}
