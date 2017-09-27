package com.mygdx.game.Components.Combat;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;

public class RangedWeaponComponent implements Component{
    public Circle range;
    public float damage, attackSpeed, currentAttack = 0, attackDuration;
}
