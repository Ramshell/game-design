package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Components.WorldObjects.DynamicWOComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class MapGraphUpdaterSystem extends EntitySystem{
    Engine engine;
    MapGraph mapGraph;
    ImmutableArray<Entity> dynamicWOEntities;
    Rectangle r;

    public void addedToEngine(Engine e){
        r = new Rectangle();
        engine = e;
        mapGraph = Mappers.graph.get(e.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
    }

    public void update(float deltaTime){
        dynamicWOEntities = engine.getEntitiesFor(Family.all(
                WorldPositionComponent.class,
                WorldObjectComponent.class,
                DynamicWOComponent.class).get());
        for(Entity dynamicWOEntity: dynamicWOEntities){
            WorldObjectComponent worldObjectComponent = Mappers.world.get(dynamicWOEntity);
            WorldPositionComponent worldPositionComponent = Mappers.worldPosition.get(dynamicWOEntity);
            int fromX = (int) worldPositionComponent.position.x / ResourceMapper.tileWidth;
            int fromY = (int) worldPositionComponent.position.y / ResourceMapper.tileHeight;
            int toX   = (int) (fromX + worldObjectComponent.bounds.getRectangle().width / ResourceMapper.tileWidth);
            int toY   = (int) (fromY + worldObjectComponent.bounds.getRectangle().height / ResourceMapper.tileHeight);
            for(int i = Math.max(0, fromX - 1); i < Math.min(mapGraph.width, toX + 2); ++i){
                for(int j = Math.max(0, fromY - 1) ; j < Math.min(mapGraph.height, toY + 2); ++j){
                    r.set(i * ResourceMapper.tileWidth,
                          j * ResourceMapper.tileHeight,
                            ResourceMapper.tileWidth, ResourceMapper.tileHeight);
                    boolean nowOverlaped = worldObjectComponent.bounds.getRectangle().overlaps(r);
                    if(!nowOverlaped && mapGraph.getNode(i, j).entities.size > 0){
                        if( mapGraph.getNode(i, j).entities.size == 1 &&
                            mapGraph.getNode(i, j).entities.contains(dynamicWOEntity))
                        mapGraph.getNode(i, j).entities.remove(dynamicWOEntity);
                    }else if(nowOverlaped){
                        mapGraph.getNode(i, j).entities.add(dynamicWOEntity);
                    }
                }
            }
        }
    }
}
