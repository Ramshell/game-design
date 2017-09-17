package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;

public interface ActionBuilder {
    Action<Entity> getAction(float x, float y);
}
