package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.IsoPositionComponent;
import com.mygdx.game.Components.WorldObjects.TargetComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;

public class UnitsMovementSystem extends EntitySystem{
    private Engine engine;
    private ImmutableArray<Entity> worldUnits;

    public void addedToEngine(Engine engine) {
        worldUnits = engine.getEntitiesFor(Family.all(WorldObjectComponent.class, AnimationComponent.class,
                IsoPositionComponent.class).get());

        this.engine = engine;
    }

    public void update(float deltaTime) {
        worldUnits = engine.getEntitiesFor(Family.all(WorldObjectComponent.class, AnimationComponent.class,
                VelocityComponent.class, IsoPositionComponent.class).get());
        for(Entity e : worldUnits){
            WorldObjectComponent wo = Mappers.world.get(e);
            IsoPositionComponent pos = Mappers.isoPosition.get(e);
            VelocityComponent velocity = Mappers.velocity.get(e);
            Rectangle r = wo.bounds.getRectangle();
            Rectangle r2 = new Rectangle(r);
            Vector2 v = pos.position.cpy()
                    .add(velocity.accel.cpy().scl(1/2 * deltaTime * deltaTime)
                            .add(velocity.pos.cpy().scl(deltaTime)));
            if(MovementSystem.outsideWorld(v, r.getWidth(), r.getHeight())) continue;
            pos.position = v;
            r.setPosition(v.cpy().add(- r.width / 2 - ResourceMapper.tileWidth / 2, - r.height / 2 + ResourceMapper.tileHeight / 2));
            velocity.increment(deltaTime);
        }
    }
}
