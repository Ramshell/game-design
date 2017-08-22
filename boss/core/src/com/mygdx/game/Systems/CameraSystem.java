package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.PositionComponent;


public class CameraSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity entity : entities) {
            PositionComponent position = pm.get(entity);
            CameraComponent camera = cm.get(entity);

            camera.getCamera().position.x = position.x;
            camera.getCamera().position.y = position.y;
            camera.getCamera().update();
        }
    }
}