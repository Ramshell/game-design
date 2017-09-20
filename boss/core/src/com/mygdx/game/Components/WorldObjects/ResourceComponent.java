package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.Components.PercentageComponent;

public class ResourceComponent extends PercentageComponent{
    public ResourceComponent(int maxResources){
        maxHitPoints = maxResources;
        hitPoints = maxHitPoints;
        damageTaken = 0;
    }
}
