package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Mappers.Mappers;

public class AttackProgressionSystem extends EntitySystem{
    ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    public void update(float deltaTime) {
        entities = getEngine().getEntitiesFor(Family.all(AttackProgressionComponent.class).get());
        for(Entity e: entities){
            AttackProgressionComponent attackProgressionComponent = Mappers.attackProgressionComponentMapper.get(e);
            if(attackProgressionComponent.target == null || Mappers.healthComponentMapper.get(attackProgressionComponent.target).hitPoints <= 0){
                e.remove(AttackProgressionComponent.class);
                continue;
            }
            if(     attackProgressionComponent.currentAttack >=
                    attackProgressionComponent.attackSpeed){
                attackProgressionComponent.currentAttack = 0;
            }
            if(attackProgressionComponent.currentAttack -deltaTime < attackProgressionComponent.attackDuration &&
                attackProgressionComponent.currentAttack >= attackProgressionComponent.attackDuration){
                Mappers.healthComponentMapper.get(attackProgressionComponent.target).damageTaken = attackProgressionComponent.damage;
//                Mappers.stateComponentMapper.get(e).change(ATTACKING);
            }
            attackProgressionComponent.currentAttack +=
                    deltaTime;

            //if the unit is movable then it should stop
            if(     Mappers.velocity.get(e) != null &&
                    attackProgressionComponent.currentAttack <
                    attackProgressionComponent.attackDuration){
                Mappers.velocity.get(e).pos.setZero();
                Mappers.velocity.get(e).accel.setZero();
            }
        }
    }
}