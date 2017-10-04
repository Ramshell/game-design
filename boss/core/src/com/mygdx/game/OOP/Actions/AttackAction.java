package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.Combat.RangedWeaponComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class AttackAction extends Action<Entity>{
    Entity target;
    public AttackAction(Entity target) {
        this.target = target;
    }

    public AttackAction(float x, float y, MapGraph mapGraph, PlayerComponent playerComponent) {
        this.target = getTarget(x, y, mapGraph, playerComponent);
    }

    @Override
    public void act(Entity e) {
        RangedWeaponComponent rangedWeaponComponent = Mappers.rangedWeaponComponentMapper.get(e);
        if(rangedWeaponComponent != null)
            e.add(new AttackProgressionComponent(target,
                    rangedWeaponComponent.minDamage,
                    rangedWeaponComponent.maxDamage,
                    rangedWeaponComponent.attackSpeed,
                    rangedWeaponComponent.attackDuration));
    }

    public static Entity getTarget(float x, float y, MapGraph mapGraph, PlayerComponent playerComponent) {
        int tileX = (int) (x / ResourceMapper.tileWidth);
        int tileY = (int) (y / ResourceMapper.tileHeight);
        System.out.println(mapGraph.getNode(tileX, tileY).entities.size);
        for (Entity e : mapGraph.getNode(tileX, tileY).entities)
            if (!Mappers.player.get(e).equals(playerComponent)){
                return e;
            }
        return null;
    }
}
