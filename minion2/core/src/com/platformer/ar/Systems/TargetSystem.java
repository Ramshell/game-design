package com.platformer.ar.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.platformer.ar.Components.TargetComponent;
import com.platformer.ar.Components.TransformComponent;
import com.platformer.ar.Components.World.VelocityComponent;

public class TargetSystem extends EntitySystem{
    Vector2 aux = new Vector2();

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e: getEngine().getEntitiesFor(Family.all(TargetComponent.class, VelocityComponent.class,
                TransformComponent.class).get())){
            Vector3 v = e.getComponent(TransformComponent.class).position.cpy().scl(-1);
            aux.set(v.x, v.y);
            e.getComponent(VelocityComponent.class).pos = e.getComponent(TargetComponent.class).target.cpy()
                    .add(aux);
            e.getComponent(VelocityComponent.class).pos.nor();
            e.getComponent(VelocityComponent.class).pos.scl(e.getComponent(VelocityComponent.class).maxSpeed);
            e.getComponent(VelocityComponent.class).accel.set(e.getComponent(VelocityComponent.class).pos);
            e.getComponent(VelocityComponent.class).accel.nor();
            e.getComponent(VelocityComponent.class).accel.scl(e.getComponent(VelocityComponent.class).accelF);

        }
    }
}
