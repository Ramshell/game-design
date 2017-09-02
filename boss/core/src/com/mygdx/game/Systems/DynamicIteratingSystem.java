package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;

public abstract class DynamicIteratingSystem extends IteratingSystem{

    public DynamicIteratingSystem(Family family) {
        super(family);
    }

}
