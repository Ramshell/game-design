package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Builders.HarlandWorkerBuilder;
import com.mygdx.game.Components.StartGatheringComponent;
import com.mygdx.game.Components.StateComponent;
import com.mygdx.game.Components.WorldObjects.GatheringPowerComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Mappers.Mappers;

public class GatheringStarterSystem extends EntitySystem {
    private Engine engine;
    private ImmutableArray<Entity> gatheringEntities;

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;
        gatheringEntities = engine.getEntitiesFor(Family.all(StartGatheringComponent.class).get());
    }

    public void update(float deltaTime){
        gatheringEntities = engine.getEntitiesFor(Family.all(StartGatheringComponent.class).get());
        for(Entity e: gatheringEntities){
            StartGatheringComponent startGatheringComponent =
                    Mappers.startGatheringComponentComponentMapper.get(e);
            Entity resource = startGatheringComponent.resource;
            Entity worker = startGatheringComponent.worker;
            ResourceComponent resourceComponent = Mappers.resourceComponentMapper.get(resource);
            GatheringPowerComponent gatheringPowerComponent = Mappers.gatheringComponentComponentMapper.get(worker);
            if(workerCollecting(worker, resource)){
                int toGather = Math.min(
                        Math.min(gatheringPowerComponent.resourcesPerTick,
                                 gatheringPowerComponent.capacity - gatheringPowerComponent.current),
                        resourceComponent.currentResources);
                resourceComponent.currentResources -= toGather;
                gatheringPowerComponent.current += toGather;
                System.out.println(gatheringPowerComponent.current);
//                Mappers.worldPosition.get(worker).position.set(Mappers.worldPosition.get(resource).position);
//                Mappers.world.get(worker).bounds.getRectangle().setPosition(Mappers.worldPosition.get(resource).position);
            }
        }
    }

    public static boolean workerCollecting(Entity worker, Entity resource) {
        return Mappers.world.get(resource).bounds.getRectangle()
                .contains(Mappers.worldPosition.get(worker).position);
    }
}
