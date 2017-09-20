package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.Components.PercentageComponent;

public class HealthComponent extends PercentageComponent{
    public HealthComponent(float maxHitPoints){
        hitPoints = maxHitPoints;
        this.maxHitPoints = maxHitPoints;
        damageTaken = 0;
    }
}
