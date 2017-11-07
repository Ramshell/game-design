package com.platformer.ar.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.ar.Components.ParticleComponent;

public class ParticleSystem extends EntitySystem{

    private final SpriteBatch batch;

    public ParticleSystem(SpriteBatch batch){
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e: getEngine().getEntitiesFor(Family.all(ParticleComponent.class).get())){
            if (e.getComponent(ParticleComponent.class).particleEffect.isComplete()){
                getEngine().removeEntity(e);
                continue;
            }
            batch.begin();
            e.getComponent(ParticleComponent.class).particleEffect.draw(batch, deltaTime);
            batch.end();
        }
    }
}
