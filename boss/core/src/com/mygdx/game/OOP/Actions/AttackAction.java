package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.Combat.RangedWeaponComponent;
import com.mygdx.game.Mappers.Mappers;

public class AttackAction extends Action<Entity>{
    Entity target;
    public AttackAction(Entity target){
        this.target = target;
    }

    @Override
    public void act(Entity e) {
        RangedWeaponComponent rangedWeaponComponent = Mappers.rangedWeaponComponentMapper.get(e);
        if(rangedWeaponComponent != null)
            e.add(new AttackProgressionComponent(e,
                    rangedWeaponComponent.minDamage,
                    rangedWeaponComponent.maxDamage,
                    rangedWeaponComponent.attackSpeed,
                    rangedWeaponComponent.attackDuration));
    }
}
