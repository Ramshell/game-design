package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Builders.UnitBuilder;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.Combat.DamageSpawnComponent;
import com.mygdx.game.Components.WorldObjects.AnimationSpawnComponent;
import com.mygdx.game.Components.WorldObjects.HealthComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class DamageSystem extends EntitySystem{
    ImmutableArray<Entity> entities;
    MapGraph mapGraph;
    private TiledMapTileLayer toDelete;


    @Override
    public void addedToEngine(Engine e) {
        super.addedToEngine(e);
        mapGraph = Mappers.graph.get(e.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
        toDelete = (TiledMapTileLayer)Mappers.map.get(e.getEntitiesFor(Family.all(MapComponent.class).get()).get(0)).map.getLayers().get("background");

    }

    @Override
    public void update(float deltaTime) {
        entities = getEngine().getEntitiesFor(Family.all(HealthComponent.class).get());
        for(Entity e: entities){
            HealthComponent healthComponent = Mappers.healthComponentMapper.get(e);
            if(healthComponent.hitPoints > 0 && healthComponent.damageTaken == 0) continue;
            healthComponent.hitPoints -= healthComponent.damageTaken;
            getEngine().addEntity(new Entity().add(
                    new DamageSpawnComponent(
                            Mappers.worldPosition.get(e).position.x,
                            Mappers.worldPosition.get(e).position.y,
                            (int) healthComponent.damageTaken)));
            healthComponent.damageTaken = 0;
            if(healthComponent.hitPoints <= 0){
                if(Mappers.animation.get(e) != null && Mappers.animation.get(e).animations.containsKey(UnitBuilder.DEAD)){
                    getEngine().addEntity(new Entity().add(
                            new AnimationSpawnComponent(
                                    Mappers.worldPosition.get(e).position.x,
                                    Mappers.worldPosition.get(e).position.y,
                                    Mappers.animation.get(e).animations.get(UnitBuilder.DEAD),
                                    Mappers.animation.get(e).offsetsX.get(UnitBuilder.DEAD),
                                    Mappers.animation.get(e).offsetsY.get(UnitBuilder.DEAD))));
                }
                getEngine().removeEntity(e);
                WorldObjectComponent worldObjectComponent = Mappers.world.get(e);
                WorldPositionComponent worldPositionComponent = Mappers.worldPosition.get(e);
                if(Mappers.cellsComponentMapper.get(e) != null){
                    for (CellComponent c : Mappers.cellsComponentMapper.get(e).cells) {
                        c.layer.setCell((int) c.position.x, (int) c.position.y, null);
                        if (c.blocked) {
                            mapGraph.setCollision(c.position.x, c.position.y, false);
                            mapGraph.getNode((int)c.position.x,(int) c.position.y).entities.remove(e);
                        }
                    }
                }else {
                    int fromX = (int) worldPositionComponent.position.x / ResourceMapper.tileWidth;
                    int fromY = (int) worldPositionComponent.position.y / ResourceMapper.tileHeight;
                    int toX = (int) (fromX + worldObjectComponent.bounds.getRectangle().width / ResourceMapper.tileWidth);
                    int toY = (int) (fromY + worldObjectComponent.bounds.getRectangle().height / ResourceMapper.tileHeight);
                    for (int i = Math.max(0, fromX - 1); i < Math.min(mapGraph.width, toX + 2); ++i) {
                        for (int j = Math.max(0, fromY - 1); j < Math.min(mapGraph.height, toY + 2); ++j) {
                            mapGraph.getNode(i, j).entities.remove(e);
                        }
                    }
                }
            }else if(e instanceof UnitEntity)getEngine().addEntity(new Entity().add(
                    new AnimationSpawnComponent(
                            Mappers.worldPosition.get(e).position.x,
                            Mappers.worldPosition.get(e).position.y,
                            AssetsMapper.unitDamageAnim, -14, 0)));
        }
    }
}
