package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.platformer.ar.Components.World.GravityComponent;
import com.platformer.ar.Components.World.VelocityComponent;
import com.platformer.ar.Mappers.ResourceManager;

public class GravitySystem extends EntitySystem{
    private ComponentMapper<VelocityComponent> vcm;

    public GravitySystem() {
        vcm = ComponentMapper.getFor(VelocityComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity entity: getEngine().getEntitiesFor(Family.all(GravityComponent.class, VelocityComponent.class).get())){
            VelocityComponent vc = vcm.get(entity);
            vc.pos.add(ResourceManager.gravity.x * deltaTime, ResourceManager.gravity.y * deltaTime);
        }
    }
}