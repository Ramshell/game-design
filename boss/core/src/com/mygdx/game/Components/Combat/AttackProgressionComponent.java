package com.mygdx.game.Components.Combat;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class AttackProgressionComponent implements Component{
    public float damage, attackSpeed, currentAttack = 0, attackDuration, minDamage, maxDamage;
    public Entity target;

    public AttackProgressionComponent(Entity e, float damage, float attackSpeed,
                                      float attackDuration){
        target = e;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.attackDuration = attackDuration;
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
