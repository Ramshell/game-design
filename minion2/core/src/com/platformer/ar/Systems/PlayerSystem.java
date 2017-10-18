package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.platformer.ar.Components.StateComponent;
import com.platformer.ar.Components.World.PlayerComponent;
import com.platformer.ar.Components.World.VelocityComponent;


public class PlayerSystem extends EntitySystem{
    ComponentMapper<PlayerComponent> rcm = ComponentMapper.getFor(PlayerComponent.class);
    ComponentMapper<StateComponent> scm = ComponentMapper.getFor(StateComponent.class);
    ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);



    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        Entity entity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class, VelocityComponent.class).get()).first();
        if(entity == null) return;
        VelocityComponent vc = vcm.get(entity);
        PlayerComponent rc = rcm.get(entity);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(vc.pos.x >= 0) scm.get(entity).set("RUNNING_LEFT");
            vc.pos.set(-rc.maxSpeed, vc.pos.y);
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(vc.pos.x <= 0) scm.get(entity).set("RUNNING_RIGHT");
            vc.pos.set(rc.maxSpeed, vc.pos.y);
        }else{
            if(!scm.get(entity).get().equals("IDLE")) {
                scm.get(entity).set("IDLE");
                vc.pos.x = 0f;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            vc.pos.set(vc.pos.x, rc.jumpSpeed);
        }

    }
}
