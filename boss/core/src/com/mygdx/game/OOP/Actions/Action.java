package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;

public abstract class Action<T extends Entity> {

    public abstract void act(T e);
}
