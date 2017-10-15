package com.platformer.ar.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.platformer.ar.Components.StateComponent;

public class UselessStateSwapSystem extends IteratingSystem {

    private ComponentMapper<StateComponent> sm;

    private Array<Entity> stateQueue;

    public UselessStateSwapSystem(){
        super(Family.all(StateComponent.class).get());
        sm = ComponentMapper.getFor(StateComponent.class);
        stateQueue = new Array<Entity>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for(Entity entity:stateQueue) {
                StateComponent state = sm.get(entity);
                if (state.get().equals("IDLE")) {
                    state.set("RUNNING");
                } else {
                    state.set("IDLE");
                }
            }
        }
        stateQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        stateQueue.add(entity);
    }
}
