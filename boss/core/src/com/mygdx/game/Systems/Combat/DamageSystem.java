package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Components.WorldObjects.HealthComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class DamageSystem extends EntitySystem{
    ImmutableArray<Entity> entities;
    MapGraph mapGraph;


    @Override
    public void addedToEngine(Engine e) {
        super.addedToEngine(e);
        mapGraph = Mappers.graph.get(e.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;

    }

    @Override
    public void update(float deltaTime) {
        entities = getEngine().getEntitiesFor(Family.all(HealthComponent.class).get());
        for(Entity e: entities){
            HealthComponent healthComponent = Mappers.healthComponentMapper.get(e);
            healthComponent.hitPoints -= healthComponent.damageTaken;
            healthComponent.damageTaken = 0;
            if(healthComponent.hitPoints <= 0){
                getEngine().removeEntity(e);
                WorldObjectComponent worldObjectComponent = Mappers.world.get(e);
                WorldPositionComponent worldPositionComponent = Mappers.worldPosition.get(e);
                int fromX = (int) worldPositionComponent.position.x / ResourceMapper.tileWidth;
                int fromY = (int) worldPositionComponent.position.y / ResourceMapper.tileHeight;
                int toX   = (int) (fromX + worldObjectComponent.bounds.getRectangle().width / ResourceMapper.tileWidth);
                int toY   = (int) (fromY + worldObjectComponent.bounds.getRectangle().height / ResourceMapper.tileHeight);
                for(int i = Math.max(0, fromX - 1); i < Math.min(mapGraph.width, toX + 2); ++i) {
                    for (int j = Math.max(0, fromY - 1); j < Math.min(mapGraph.height, toY + 2); ++j) {
                        mapGraph.getNode(i, j).entities.remove(e);
                    }
                }
            }
        }
    }
}
