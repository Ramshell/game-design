package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.mygdx.game.Components.PlayerComponent;

public class CollectResourcesVictoryCondition extends VictoryCondition{
    PlayerComponent playerComponent;
    private int resourcesToWin;

    public CollectResourcesVictoryCondition(Engine engine, PlayerComponent playerComponent, int resources) {
        super(engine, "Gather " + String.valueOf(resources) + " EoL");
        this.playerComponent = playerComponent;
        this.resourcesToWin = resources;
    }

    @Override
    boolean victory() {
        return playerComponent.resources >= resourcesToWin;
    }
}
