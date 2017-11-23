package com.mygdx.game.Components.Combat;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

public class AttackProgressionComponent implements Component{
    public float damage, attackSpeed,
            currentAttack = 0, attackDuration,
            minDamage, maxDamage,
            timeOutOfRange = 0;
    public Entity target;
    public boolean atRange = false, areaDamage = false;

    public AttackProgressionComponent(Entity e, float damage, float attackSpeed,
                                      float attackDuration){
        target = e;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.attackDuration = attackDuration;
    }

    public AttackProgressionComponent(Entity e, float damage, float attackSpeed,
                                      float attackDuration, boolean areaDamage){
        this(e, damage, attackSpeed, attackDuration);
        this.areaDamage = areaDamage;
    }

    public AttackProgressionComponent(Entity e, float minDamage, float maxDamage, float attackSpeed,
                                      float attackDuration, boolean areaDamage){
        this(e, minDamage, maxDamage, attackSpeed, attackDuration);
        this.areaDamage = areaDamage;
    }

    public AttackProgressionComponent(Entity e, float minDamage, float maxDamage, float attackSpeed,
                                      float attackDuration){
        target = e;
        this.attackSpeed = attackSpeed;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.attackDuration = attackDuration;
    }
}
