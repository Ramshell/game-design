package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Builders.HarlandWorkerBuilder;
import com.mygdx.game.Components.StartGatheringComponent;
import com.mygdx.game.Components.StateComponent;
import com.mygdx.game.Components.VelocityComponent;
import com.mygdx.game.Components.WorldObjects.GatheringPowerComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;

public class UnitStateSystem extends EntitySystem{
    ImmutableArray<Entity> entities;
    Engine engine;


    public void addedToEngine(Engine engine){
        this.entities = engine.getEntitiesFor(Family.all(VelocityComponent.class, StateComponent.class).get());
        this.engine = engine;
    }

    public void update(float deltaTime){
        this.entities = engine.getEntitiesFor(Family.all(VelocityComponent.class, StateComponent.class).get());
        for(Entity e: entities){
            if(Mappers.velocity.get(e).pos.x > 0){
                if(Mappers.velocity.get(e).pos.y > 0)
                    Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.MOVE_RIGHT_TOP);
                else Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.MOVE_RIGHT_BOTTOM);
            }
            else if(Mappers.velocity.get(e).pos.x < 0){
                if(Mappers.velocity.get(e).pos.y > 0)
                    Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.MOVE_LEFT_TOP);
                else Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.MOVE_LEFT_BOTTOM);
            }
            else if(Mappers.velocity.get(e).pos.y > 0)
                Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.MOVE_RIGHT_TOP);
            else if(Mappers.velocity.get(e).pos.y < 0)
                Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.MOVE_RIGHT_BOTTOM);
            else Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.IDLE);
            if(Mappers.startGatheringComponentComponentMapper.get(e) != null &&
                    Mappers.startGatheringComponentComponentMapper.get(e).gathering &&
                    working(Mappers.startGatheringComponentComponentMapper.get(e))){
                Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.WATER_GATHERING);
            }else if(Mappers.attackProgressionComponentMapper.get(e) != null &&
                    Mappers.attackProgressionComponentMapper.get(e).currentAttack < Mappers.attackProgressionComponentMapper.get(e).attackDuration){
            }
        }
    }

    private boolean working(StartGatheringComponent startGatheringComponent) {
        return GatheringStarterSystem.workerCollecting(startGatheringComponent.worker, startGatheringComponent.resource);
    }
}
