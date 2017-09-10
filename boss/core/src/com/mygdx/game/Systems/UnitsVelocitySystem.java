package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Components.WorldObjects.TargetComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

public class UnitsVelocitySystem extends EntitySystem{
    private Engine engine;
    private ImmutableArray<Entity> worldBuildings;
    private ImmutableArray<Entity> worldUnits;

    public void addedToEngine(Engine engine) {
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        worldUnits = engine.getEntitiesFor(Family.all(TextureComponent.class, WorldObjectComponent.class, AnimationComponent.class,
                PositionComponent.class).get());

        this.engine = engine;
    }

    public void update(float deltaTime) {
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        worldUnits = engine.getEntitiesFor(Family.all(AnimationComponent.class,
                VelocityComponent.class, WorldPositionComponent.class, TargetComponent.class).get());
        for(Entity e : worldUnits){
            WorldPositionComponent pos = Mappers.worldPosition.get(e);
            VelocityComponent velocity = Mappers.velocity.get(e);
            TargetComponent target = Mappers.target.get(e);
            if(target.target == null){
                velocity.pos.setZero();
                e.remove(TargetComponent.class);
                continue;
            }
            if (pos.position.epsilonEquals(target.target, 0.5f) ) {
                target.nextTarget();
                if(target.target == null){
                    velocity.pos.setZero();
                    e.remove(TargetComponent.class);
                    continue;
                }else{
                    velocity.pos = target.target.cpy().add(pos.position.cpy().scl(-1));
                    velocity.clamp();
                }
            }
            velocity.pos = target.target.cpy().add(pos.position.cpy().scl(-1));
            velocity.pos.nor();
            velocity.pos.scl(velocity.maxSpeed);
        }
    }


}
