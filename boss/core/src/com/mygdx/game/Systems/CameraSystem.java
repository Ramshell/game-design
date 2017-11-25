package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;


public class CameraSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity entity : entities) {
            PositionComponent position = pm.get(entity);
            CameraComponent camera = cm.get(entity);
            position.pos.x = Math.max(
                    cameraLowerBound(camera, true),
                    Math.min(position.pos.x,
                            cameraUpperBound(camera, true)));
            position.pos.y = Math.max(
                    cameraLowerBound(camera, false),
                    Math.min(position.pos.y,
                            cameraUpperBound(camera, false)));
            camera.getCamera().position.x = position.pos.x;
            camera.getCamera().position.y = position.pos.y;
            camera.getCamera().update();
        }
    }

    private float cameraUpperBound(CameraComponent cameraComponent, boolean width){
        if(width)
            return ResourceMapper.tileWidth * ResourceMapper.width - cameraComponent.getCamera().viewportWidth / 2;
        else
            return ResourceMapper.tileHeight * ResourceMapper.height - cameraComponent.getCamera().viewportHeight / 2;
    }

    private float cameraLowerBound(CameraComponent cameraComponent, boolean width){
        if(width)
            return cameraComponent.getCamera().viewportWidth / 2;
        else
            return cameraComponent.getCamera().viewportHeight / 2;
    }
}
