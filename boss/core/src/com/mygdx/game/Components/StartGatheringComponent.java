package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class StartGatheringComponent implements Component {
    public Entity resource, worker;
    public StartGatheringComponent(Entity resource, Entity worker){
        this.resource = resource;
        this.worker = worker;
    }
}
