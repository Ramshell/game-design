package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.platformer.ar.Components.TransformComponent;
import com.platformer.ar.Components.World.PositionComponent;
import com.platformer.ar.Components.World.SolidComponent;
import com.platformer.ar.Components.World.VelocityComponent;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {}

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    public void update(float deltaTime) {
        for (Entity entity : getEngine().getEntitiesFor(Family.all(TransformComponent.class, VelocityComponent.class).get())) {
            TransformComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            Vector3 v = position.position.cpy()
                    .add(velocity.accel.x * 1/2 * deltaTime * deltaTime, velocity.accel.y * 1/2 * deltaTime * deltaTime, 0)
                            .add(velocity.pos.x * deltaTime, velocity.pos.y * deltaTime, 0);
            position.position.set(v);
            velocity.increment(deltaTime);
        }
    }

    public static boolean outsideWorld(Vector3 check){
        return check.x < 0 || check.y < 0;
    }
}
