package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.WorldObjects.PlaySoundComponent;
import com.mygdx.game.Components.WorldObjects.WOSoundComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;

public class WOSoundSystem extends EntitySystem{

    CameraComponent cameraComponent;
    float maxDistance = Math.max(ResourceMapper.width * ResourceMapper.tileWidth, ResourceMapper.height * ResourceMapper.tileHeight);

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        cameraComponent = Mappers.camera.get(getEngine().getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get()).first());
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e : getEngine().getEntitiesFor(
                Family.all(
                        WOSoundComponent.class,
                        PlaySoundComponent.class
                ).get())){
            WOSoundComponent ws = Mappers.woSoundComponentMapper.get(e);
            Sound s = ws.sounds.get(Mappers.playSoundComponentMapper.get(e).mappingSound).random();
            float volume = 1;
            if(Mappers.world.get(e) != null){
                float distance = cameraComponent.getCamera().position.cpy().sub(Mappers.world.get(e).bounds.getRectangle().getX(), Mappers.world.get(e).bounds.getRectangle().getY(), 0).len();
                if(distance > cameraComponent.getCamera().viewportHeight || distance > cameraComponent.getCamera().viewportWidth)
                    volume = 0.3f;
            }
            s.play(volume);
            ws.lastSound = s;
            e.remove(PlaySoundComponent.class);
        }
    }
}
