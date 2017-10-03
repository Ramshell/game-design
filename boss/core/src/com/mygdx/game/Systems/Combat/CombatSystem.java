package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.Builders.UnitBuilder;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.Combat.RangedWeaponComponent;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Components.WorldObjects.PatrolComponent;
import com.mygdx.game.Components.WorldObjects.TargetComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class CombatSystem extends EntitySystem{
    MapGraph mapGraph;


    @Override
    public void addedToEngine(Engine e) {
        super.addedToEngine(e);
        mapGraph = Mappers.graph.get(e.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
    }

    public void update(float deltaTime){
        for(Entity e: getEngine().getEntitiesFor(Family.all(RangedWeaponComponent.class, WorldPositionComponent.class).get())){
            WorldPositionComponent worldPositionComponent = Mappers.worldPosition.get(e);
            RangedWeaponComponent rangedWeaponComponent = Mappers.rangedWeaponComponentMapper.get(e);
            rangedWeaponComponent.range.setPosition(
                    worldPositionComponent.position.x + ResourceMapper.tileWidth / 2,
                    worldPositionComponent.position.y + ResourceMapper.tileHeight / 2);
            ////////////////////// ignore non idle objects ////////////////////////////
            if(Mappers.stateComponentMapper.get(e).state != UnitBuilder.IDLE ||
                    (Mappers.patrolComponentMapper.get(e) == null && Mappers.target.get(e) != null)) continue;
            ///////////////////////////////////////////////////////////////////////////
            AttackProgressionComponent attackProgressionComponent = Mappers.attackProgressionComponentMapper.get(e);
            if(attackProgressionComponent != null && atRange(rangedWeaponComponent, attackProgressionComponent.target)) continue;
            calculateAttack(e, rangedWeaponComponent);
        }
    }

    public static boolean atRange(RangedWeaponComponent rangedWeaponComponent, Entity target) {
        return Intersector.overlaps(rangedWeaponComponent.range, Mappers.world.get(target).bounds.getRectangle());
    }

    private void calculateAttack(Entity e, RangedWeaponComponent rangedWeaponComponent) {
        int fromX = (int) (rangedWeaponComponent.range.x - rangedWeaponComponent.range.radius) / ResourceMapper.tileWidth;
        int fromY = (int) (rangedWeaponComponent.range.y - rangedWeaponComponent.range.radius) / ResourceMapper.tileHeight;
        int toX = (int) (rangedWeaponComponent.range.x + rangedWeaponComponent.range.radius) / ResourceMapper.tileWidth + 1;
        int toY = (int) (rangedWeaponComponent.range.y + rangedWeaponComponent.range.radius) / ResourceMapper.tileHeight + 1;
        for(int i = Math.max(0, fromX); i < Math.min(mapGraph.width, toX); ++i){
            for(int j = Math.max(0, fromY); j < Math.min(mapGraph.height, toY); ++j){
                for(Entity entity: mapGraph.getNode(i, j).entities)
                    if(!entity.equals(e) && !Mappers.player.get(entity).equals(Mappers.player.get(e)) && atRange(rangedWeaponComponent, entity)){
                        e.add(new AttackProgressionComponent(entity, rangedWeaponComponent.minDamage, rangedWeaponComponent.maxDamage, rangedWeaponComponent.attackSpeed, rangedWeaponComponent.attackDuration));
                        e.remove(PatrolComponent.class);
                        e.remove(TargetComponent.class);
                        return;
                    }
            }
        }
        AttackProgressionComponent attackProgressionComponent = Mappers.attackProgressionComponentMapper.get(e);
        if(attackProgressionComponent != null) e.remove(AttackProgressionComponent.class);
    }
}
