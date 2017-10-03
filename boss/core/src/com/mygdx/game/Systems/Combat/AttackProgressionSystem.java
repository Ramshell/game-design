package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.MoveAction;

import java.util.Random;

public class AttackProgressionSystem extends EntitySystem{
    ImmutableArray<Entity> entities;
    Random rand = new Random();
    MoveAction m;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        m = new MoveAction(0,0, Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph);
    }

    public void update(float deltaTime) {
        entities = getEngine().getEntitiesFor(Family.all(AttackProgressionComponent.class).get());
        for(Entity e: entities){
            AttackProgressionComponent attackProgressionComponent = Mappers.attackProgressionComponentMapper.get(e);
            if(attackProgressionComponent.target == null || Mappers.healthComponentMapper.get(attackProgressionComponent.target).hitPoints <= 0){
                e.remove(AttackProgressionComponent.class);
                continue;
            }
            if(!CombatSystem.atRange(Mappers.rangedWeaponComponentMapper.get(e), attackProgressionComponent.target)){
                m.x = (int) (Mappers.worldPosition.get(attackProgressionComponent.target).position.x / ResourceMapper.tileWidth);
                m.y = (int) (Mappers.worldPosition.get(attackProgressionComponent.target).position.y / ResourceMapper.tileHeight);
                m.act(e);
                attackProgressionComponent.atRange = false;
                continue;
            }
            attackProgressionComponent.atRange = true;
            if(     attackProgressionComponent.currentAttack >=
                    attackProgressionComponent.attackSpeed){
                attackProgressionComponent.currentAttack = 0;
            }
            if(attackProgressionComponent.currentAttack -deltaTime < attackProgressionComponent.attackDuration &&
                attackProgressionComponent.currentAttack >= attackProgressionComponent.attackDuration){
                Mappers.healthComponentMapper.get(attackProgressionComponent.target).damageTaken = rand.nextFloat() * (attackProgressionComponent.maxDamage - attackProgressionComponent.minDamage) + attackProgressionComponent.minDamage;
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
