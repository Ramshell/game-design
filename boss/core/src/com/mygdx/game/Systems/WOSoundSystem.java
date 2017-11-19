package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Components.WorldObjects.PlaySoundComponent;
import com.mygdx.game.Components.WorldObjects.WOSoundComponent;
import com.mygdx.game.Mappers.Mappers;

public class WOSoundSystem extends EntitySystem{
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
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
            s.play();
            ws.lastSound = s;
            e.remove(PlaySoundComponent.class);
        }
    }
}
