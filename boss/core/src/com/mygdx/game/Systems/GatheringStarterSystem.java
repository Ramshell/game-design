package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.GatheringPowerComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Components.WorldObjects.SpawnComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.MoveAction;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class GatheringStarterSystem extends IntervalSystem {
    private Engine engine;
    private ImmutableArray<Entity> gatheringEntities;
    private ImmutableArray<Entity> buildingEntities;
    private MapGraph mapGraph;

    public GatheringStarterSystem(){
        super(0.5f);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;
        mapGraph = Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
        gatheringEntities = engine.getEntitiesFor(Family.all(StartGatheringComponent.class).get());
    }

    protected void updateInterval(){
        gatheringEntities = engine.getEntitiesFor(Family.all(StartGatheringComponent.class).get());
        buildingEntities = engine.getEntitiesFor(Family.all(WorldObjectComponent.class,
                SpawnComponent.class).get());
        for(Entity e: gatheringEntities){
            StartGatheringComponent startGatheringComponent =
                    Mappers.startGatheringComponentComponentMapper.get(e);
            Entity resource = startGatheringComponent.resource;
            Entity worker = startGatheringComponent.worker;
            ResourceComponent resourceComponent = Mappers.resourceComponentMapper.get(resource);
            GatheringPowerComponent gatheringPowerComponent = Mappers.gatheringComponentMapper.get(worker);
            if(workerCollecting(worker, resource) &&
                    startGatheringComponent.gathering){
                float toGather = Math.min(
                        Math.min(gatheringPowerComponent.resourcesPerTick,
                                 gatheringPowerComponent.capacity - gatheringPowerComponent.current),
                        resourceComponent.hitPoints);
                resourceComponent.hitPoints -= toGather;
                gatheringPowerComponent.current += toGather;
                if(resourceComponent.hitPoints <= 0 ||
                        gatheringPowerComponent.capacity == gatheringPowerComponent.current){
                    Vector2 v = nearestDeposit(worker);
                    startGatheringComponent.gathering = false;
                    if(v == null) continue;
                    startGatheringComponent.deposit = v;
                    new MoveAction(v.x, v.y, mapGraph).act(worker);
                }
            }else if(!startGatheringComponent.gathering &&
                    startGatheringComponent.deposit != null &&
                    Mappers.world.get(worker).bounds.getRectangle().contains(startGatheringComponent.deposit)){
                Vector2 v = Mappers.worldPosition.get(resource).position;
                startGatheringComponent.deposit = null;
                startGatheringComponent.gathering = true;
                Mappers.player.get(worker).resources += gatheringPowerComponent.current;
                gatheringPowerComponent.current = 0;
                if(resourceComponent.hitPoints == 0){
                    e.remove(StartGatheringComponent.class);
                }else {
                    new MoveAction(v.x, v.y, mapGraph).act(worker);
                }
            }
        }
    }

    private Vector2 nearestDeposit(Entity worker) {
        Vector2 v = Mappers.worldPosition.get(worker).position.cpy();
        v.set(((int)v.x / ResourceMapper.tileWidth),
              ((int)v.y / ResourceMapper.tileHeight));
        Vector2 v2;
        Vector2 res = null;
        int minimumDistance = ResourceMapper.tileHeight * ResourceMapper.tileWidth;
        for(Entity building: buildingEntities){
            if(!Mappers.player.get(building).equals(Mappers.player.get(worker))) continue;
            v2 = Mappers.spawn.get(building).nextSpawnTile(mapGraph);
            if(v2 == null) continue;
            int distance = (int) (Math.abs(v.x - v2.x) + Math.abs( v.y - v2.y));
            if(distance < minimumDistance) {
                minimumDistance = distance;
                res = v2.scl(ResourceMapper.tileWidth, ResourceMapper.tileHeight);
            }
        }
        return res;
    }

    public static boolean workerCollecting(Entity worker, Entity resource) {
        return Mappers.world.get(resource).bounds.getRectangle()
                .contains(Mappers.worldPosition.get(worker).position);
    }
}
