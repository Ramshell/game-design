package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.platformer.ar.Components.BackgroundComponent;
import com.platformer.ar.Components.CameraComponent;
import com.platformer.ar.Components.TransformComponent;
import com.platformer.ar.Components.World.VelocityComponent;

public class BackgroundSystem extends EntitySystem{

    ComponentMapper<BackgroundComponent> backgroundComponentMapper = ComponentMapper.getFor(BackgroundComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e : getEngine().getEntitiesFor(Family.all(BackgroundComponent.class).get())){
            BackgroundComponent backgroundComponent = backgroundComponentMapper.get(e);
            float scale = backgroundComponent.background.get(0).getComponent(TransformComponent.class).scale.x;
            if(backgroundComponent.target.getComponent(TransformComponent.class).position.x < backgroundComponent.background.get(1).getComponent(TransformComponent.class).position.x - backgroundComponent.background.get(1).background.getRegionWidth() * scale){
                backgroundComponent.background.swap(0, 2);
                backgroundComponent.background.swap(1, 2);
                backgroundComponent.background.get(0).getComponent(TransformComponent.class).position.set(
                        backgroundComponent.background.get(1).getComponent(TransformComponent.class).position.cpy().sub(
                                backgroundComponent.background.get(1).background.getRegionWidth() * scale, 0, 0
                        ));
            }else if (backgroundComponent.target.getComponent(TransformComponent.class).position.x > backgroundComponent.background.get(1).getComponent(TransformComponent.class).position.x + backgroundComponent.background.get(1).background.getRegionWidth() * scale){
                backgroundComponent.background.swap(0, 2);
                backgroundComponent.background.swap(0, 1);
                backgroundComponent.background.get(2).getComponent(TransformComponent.class).position.set(
                        backgroundComponent.background.get(1).getComponent(TransformComponent.class).position.cpy().add(
                                backgroundComponent.background.get(1).background.getRegionWidth() * scale, 0, 0
                        ));
            }
            CameraComponent cameraComponent = ComponentMapper.getFor(CameraComponent.class).get(getEngine().getEntitiesFor(Family.all(CameraComponent.class).get()).first());
            float multiplier = 1;
            if(cameraComponent.camera.position.x == cameraComponent.boundRightX || cameraComponent.camera.position.x == cameraComponent.boundLeftX)
                multiplier = 0;
            for(Entity background : backgroundComponent.background){
                background.getComponent(VelocityComponent.class).pos.x = (backgroundComponent.target.getComponent(VelocityComponent.class).pos.x / backgroundComponent.nearBy) * multiplier;
            }
        }
    }


}
