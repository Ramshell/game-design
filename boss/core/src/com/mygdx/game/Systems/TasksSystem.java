package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.Components.WorldObjects.Buildings.TasksComponent;
import com.mygdx.game.Mappers.Mappers;

public class TasksSystem extends EntitySystem{
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e: getEngine().getEntitiesFor(Family.all(TasksComponent.class).get())){
            Mappers.tasksComponentMapper.get(e).processBuildQueue(deltaTime);
        }
    }
}
