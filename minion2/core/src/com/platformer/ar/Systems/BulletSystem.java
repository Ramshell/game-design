package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.platformer.ar.Components.AnimationComponent;
import com.platformer.ar.Components.StateComponent;
import com.platformer.ar.Components.World.BulletComponent;
import com.platformer.ar.Components.World.VelocityComponent;

public class BulletSystem extends EntitySystem{
    ComponentMapper<BulletComponent> bcm = ComponentMapper.getFor(BulletComponent.class);
    ComponentMapper<StateComponent> scm = ComponentMapper.getFor(StateComponent.class);
    ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    ComponentMapper<AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e : getEngine().getEntitiesFor(
                Family.all(BulletComponent.class,
                        StateComponent.class,
                        VelocityComponent.class).get())){
            if(scm.get(e).get().equals("MUZZLE")){
                if(acm.get(e).animations.get(scm.get(e).get()).isAnimationFinished(scm.get(e).time))
                    getEngine().removeEntity(e);
                continue;
            }
            if(vcm.get(e).pos.x == 0){
                scm.get(e).set("MUZZLE");
                scm.get(e).time = 0;
            }
        }
    }
}
