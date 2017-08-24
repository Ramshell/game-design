package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.VelocityComponent;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private final float maxVelocity = 3000;

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity entity : entities) {
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            position.pos
                    .add(velocity.accel.cpy().scl(1/2 * deltaTime * deltaTime)
                    .add(velocity.pos.cpy().scl(deltaTime)));
            velocity.pos.add(velocity.accel.cpy().scl(deltaTime));
            velocity.pos.x = Math.min(velocity.pos.x, maxVelocity);
            velocity.pos.y = Math.min(velocity.pos.y, maxVelocity);
            velocity.pos.x = Math.max(velocity.pos.x, -maxVelocity);
            velocity.pos.y = Math.max(velocity.pos.y, -maxVelocity);
        }
    }
}
