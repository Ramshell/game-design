package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.OrderedSet;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.Combat.RangedWeaponComponent;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Components.WorldObjects.PlaySoundComponent;
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
    OrderedSet<Entity> areaUnits = new OrderedSet<Entity>();

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
            RangedWeaponComponent rangedWeaponComponent = Mappers.rangedWeaponComponentMapper.get(e);
            if(!CombatSystem.atRange(rangedWeaponComponent, attackProgressionComponent.target)){
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
            if(attackProgressionComponent.currentAttack == 0){
                if(Mappers.playSoundComponentMapper.get(e) != null){
                    Mappers.playSoundComponentMapper.get(e).mappingSound = "before_attack";
                }else{
                    e.add(new PlaySoundComponent("before_attack"));
                }
                Mappers.stateComponentMapper.get(e).time = 0;
            }

            if(attackProgressionComponent.currentAttack < attackProgressionComponent.attackDuration &&
                attackProgressionComponent.currentAttack + deltaTime >= attackProgressionComponent.attackDuration){
                if(!rangedWeaponComponent.area)
                    Mappers.healthComponentMapper.get(attackProgressionComponent.target).damageTaken += (int) (rand.nextFloat() * (attackProgressionComponent.maxDamage - attackProgressionComponent.minDamage) + attackProgressionComponent.minDamage);
                else {
                    calculateAttack(e, rangedWeaponComponent, attackProgressionComponent);
                }
                if(Mappers.playSoundComponentMapper.get(e) != null){
                    Mappers.playSoundComponentMapper.get(e).mappingSound = "attack";
                }else{
                    e.add(new PlaySoundComponent("attack"));
                }
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


    private void calculateAttack(Entity e, RangedWeaponComponent rangedWeaponComponent,
                                 AttackProgressionComponent attackProgressionComponent) {
        areaUnits.clear();
        int fromX = (int) (rangedWeaponComponent.range.x - rangedWeaponComponent.range.radius) / ResourceMapper.tileWidth;
        int fromY = (int) (rangedWeaponComponent.range.y - rangedWeaponComponent.range.radius) / ResourceMapper.tileHeight;
        int toX = (int) (rangedWeaponComponent.range.x + rangedWeaponComponent.range.radius) / ResourceMapper.tileWidth + 1;
        int toY = (int) (rangedWeaponComponent.range.y + rangedWeaponComponent.range.radius) / ResourceMapper.tileHeight + 1;
        for(int i = Math.max(0, fromX); i < Math.min(mapGraph.width, toX); ++i){
            for(int j = Math.max(0, fromY); j < Math.min(mapGraph.height, toY); ++j){
                for(Entity entity: mapGraph.getNode(i, j).entities)
                    if(!entity.equals(e) && !Mappers.player.get(entity).equals(Mappers.player.get(e)) && CombatSystem.atRange(rangedWeaponComponent, entity) && !areaUnits.contains(entity)){
                        Mappers.healthComponentMapper.get(entity).damageTaken += (int) (rand.nextFloat() * (attackProgressionComponent.maxDamage - attackProgressionComponent.minDamage) + attackProgressionComponent.minDamage);
                        areaUnits.add(entity);
                    }
            }
        }
    }
}
