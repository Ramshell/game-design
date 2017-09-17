package com.mygdx.game.Components.WorldObjects.Tasks;

import com.badlogic.ashley.core.Entity;

public interface Task<T extends Entity> {
    boolean finished(T e);
}
