package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Mappers.Mappers;

public class ResourcesSystem extends EntitySystem{
    ImmutableArray<Entity> resources;
    Engine engine;

    public void addedToEngine(Engine engine) {
        this.engine = engine;
    }

    public void update(float deltaTime){
        resources = engine.getEntitiesFor(Family.all(ResourceComponent.class).get());
        removeUselessResoureces();
    }

    private void removeUselessResoureces() {
        for(Entity resource: resources) {
            ResourceComponent re = Mappers.resourceComponentMapper.get(resource);
            if (re.hitPoints == 0)
                engine.removeEntity(resource);
        }
    }
}
