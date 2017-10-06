package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Builders.HarlandWorkerBuilder;
import com.mygdx.game.Builders.UnitBuilder;
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
    private Vector2 attackingPosition;


    public void addedToEngine(Engine engine){
        this.entities = engine.getEntitiesFor(Family.all(VelocityComponent.class, StateComponent.class).get());
        this.engine = engine;
        attackingPosition = new Vector2();
    }

    public void update(float deltaTime){
        this.entities = engine.getEntitiesFor(Family.all(VelocityComponent.class, StateComponent.class).get());
        for(Entity e: entities){

            if(Mappers.attackProgressionComponentMapper.get(e) != null             &&
                    Mappers.attackProgressionComponentMapper.get(e).target != null &&
                    Mappers.attackProgressionComponentMapper.get(e).atRange        &&
                    Mappers.attackProgressionComponentMapper.get(e).currentAttack < Mappers.attackProgressionComponentMapper.get(e).attackDuration){
                Entity target = Mappers.attackProgressionComponentMapper.get(e).target;
                attackingPosition.set(Mappers.worldPosition.get(target).position.cpy().sub(Mappers.worldPosition.get(e).position));
                if(attackingPosition.x > 0){
                    if(attackingPosition.y > 0)
                        Mappers.stateComponentMapper.get(e).set(UnitBuilder.ATTACKING_RIGHT_TOP);
                    else Mappers.stateComponentMapper.get(e).set(UnitBuilder.ATTACKING_RIGHT_BOTTOM);
                }
                else if(attackingPosition.x < 0){
                    if(attackingPosition.y > 0)
                        Mappers.stateComponentMapper.get(e).set(UnitBuilder.ATTACKING_LEFT_TOP);
                    else Mappers.stateComponentMapper.get(e).set(UnitBuilder.ATTACKING_LEFT_BOTTOM);
                }
                else if(attackingPosition.y > 0)
                        Mappers.stateComponentMapper.get(e).set(UnitBuilder.ATTACKING_RIGHT_TOP);
                else if(attackingPosition.y < 0)
                        Mappers.stateComponentMapper.get(e).set(UnitBuilder.ATTACKING_LEFT_BOTTOM);
                continue;
            }
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
            if(     Mappers.velocity.get(e).pos.isZero() &&
                    Mappers.startGatheringComponentComponentMapper.get(e) != null &&
                    Mappers.startGatheringComponentComponentMapper.get(e).gathering &&
                    working(Mappers.startGatheringComponentComponentMapper.get(e))){
                Mappers.stateComponentMapper.get(e).set(HarlandWorkerBuilder.WATER_GATHERING);
            }
        }
    }

    private boolean working(StartGatheringComponent startGatheringComponent) {
        return GatheringStarterSystem.workerCollecting(startGatheringComponent.worker, startGatheringComponent.resource);
    }
}
