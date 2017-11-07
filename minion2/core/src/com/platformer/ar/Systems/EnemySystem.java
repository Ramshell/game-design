package com.platformer.ar.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector3;
import com.platformer.ar.Components.EnemyComponent;
import com.platformer.ar.Components.TargetComponent;
import com.platformer.ar.Components.TransformComponent;
import com.platformer.ar.Components.World.PlayerComponent;
import com.platformer.ar.Components.World.VelocityComponent;

public class EnemySystem extends EntitySystem {
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e: getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get())){
            EnemyComponent enemyComponent = e.getComponent(EnemyComponent.class);
            Entity player = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
            if(!enemyComponent.attacking && enemyComponent.delay > enemyComponent.maxDealey && player.getComponent(TransformComponent.class).position.cpy().sub(e.getComponent(TransformComponent.class).position).len() < 200){
                enemyComponent.attacking = true;
                TargetComponent targetComponent = new TargetComponent();
                targetComponent.target.set(player.getComponent(TransformComponent.class).position.x, player.getComponent(TransformComponent.class).position.y);
                enemyComponent.delay = 0;
                e.add(targetComponent);
            }else if (enemyComponent.attacking && e.getComponent(TransformComponent.class).position.epsilonEquals(new Vector3(e.getComponent(TargetComponent.class).target.x, e.getComponent(TargetComponent.class).target.y, e.getComponent(TransformComponent.class).position.z), 1f)){
                e.remove(TargetComponent.class);
                e.getComponent(VelocityComponent.class).pos.setZero();
                e.getComponent(VelocityComponent.class).accel.setZero();
                enemyComponent.attacking = false;
            }else {
                enemyComponent.delay += deltaTime;
            }
        }
    }
}
