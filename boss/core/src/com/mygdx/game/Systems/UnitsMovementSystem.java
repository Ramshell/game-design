package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class UnitsMovementSystem extends EntitySystem{
    private MapGraph mapGraph;

    private Engine engine;
    private ImmutableArray<Entity> worldUnits;


    public void addedToEngine(Engine engine) {
        worldUnits = engine.getEntitiesFor(Family.all(WorldObjectComponent.class, AnimationComponent.class,
                WorldPositionComponent.class).get());

        this.engine = engine;
        this.mapGraph = Mappers.graph.get(engine.getEntitiesFor(Family.one(MapGraphComponent.class).get())
                .first()).mapGraph;
    }

    public void update(float deltaTime) {
        worldUnits = engine.getEntitiesFor(Family.all(WorldObjectComponent.class, AnimationComponent.class,
                VelocityComponent.class, WorldPositionComponent.class).get());
        for(Entity e : worldUnits){
            WorldObjectComponent wo = Mappers.world.get(e);
            WorldPositionComponent pos = Mappers.worldPosition.get(e);
            VelocityComponent velocity = Mappers.velocity.get(e);
            Rectangle r = wo.bounds.getRectangle();
            Vector2 v = pos.position.cpy()
                    .add(velocity.accel.cpy().scl(1/2 * deltaTime * deltaTime)
                            .add(velocity.pos.cpy().scl(deltaTime)));
            if(MovementSystem.outsideWorld(v, r.getWidth(), r.getHeight())) continue;
            pos.position = v;
            r.setPosition(v);
            velocity.increment(deltaTime);
            if(velocity.oneFrame) {
                velocity.pos.setZero();
                velocity.accel.setZero();
                velocity.oneFrame = false;
            }
        }
    }
}
