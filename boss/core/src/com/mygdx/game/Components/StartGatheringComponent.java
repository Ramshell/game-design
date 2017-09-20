package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class StartGatheringComponent implements Component {
    public Entity resource, worker;
    public boolean gathering = true;
    public Vector2 deposit = null;
    public StartGatheringComponent(Entity resource, Entity worker){
        this.resource = resource;
        this.worker = worker;
    }
}
