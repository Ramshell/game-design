package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Components.WorldObjects.TargetComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.MoveAction;
import com.mygdx.game.PathfindingUtils.MapGraph;

import java.util.Random;

public class AttackProgressionSystem extends EntitySystem{
    ImmutableArray<Entity> entities;
    Random rand = new Random();
    MoveAction m;
    MapGraph mapGraph;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        m = new MoveAction(0,0, Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph);
        mapGraph = Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;

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
                if(attackProgressionComponent.timeOutOfRange == 0) {
                    if(Mappers.spawn.get(attackProgressionComponent.target) == null) {
                        m.x = (int) (Mappers.worldPosition.get(attackProgressionComponent.target).position.x / ResourceMapper.tileWidth);
                        m.y = (int) (Mappers.worldPosition.get(attackProgressionComponent.target).position.y / ResourceMapper.tileHeight);
                    }else{
                        Vector2 v = Mappers.spawn.get(attackProgressionComponent.target).nextSpawnTile(mapGraph);
                        m.x = (int) v.x;
                        m.y = (int) v.y;
                    }
                    m.act(e);
                }
                attackProgressionComponent.atRange = false;
                attackProgressionComponent.timeOutOfRange += attackProgressionComponent.timeOutOfRange >= 1 ? - attackProgressionComponent.timeOutOfRange : deltaTime;
                continue;
            }
            attackProgressionComponent.timeOutOfRange = 0;
            attackProgressionComponent.atRange = true;
            e.remove(TargetComponent.class);
            if(     attackProgressionComponent.currentAttack >=
                    attackProgressionComponent.attackSpeed){
                attackProgressionComponent.currentAttack = 0;
            }
            if(attackProgressionComponent.currentAttack < attackProgressionComponent.attackDuration &&
                attackProgressionComponent.currentAttack + deltaTime >= attackProgressionComponent.attackDuration){
                Mappers.healthComponentMapper.get(attackProgressionComponent.target).damageTaken += (int) (rand.nextFloat() * (attackProgressionComponent.maxDamage - attackProgressionComponent.minDamage) + attackProgressionComponent.minDamage);
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
