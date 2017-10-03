package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Systems.MatchTimeSystem;

public class DeadLineDefeatCondition extends DefeatCondition{
    PlayerComponent playerComponent;
    int deadline;

    public DeadLineDefeatCondition(Engine engine, PlayerComponent playerComponent, int segs) {
        super(engine, "run out of time");
        this.playerComponent = playerComponent;
        deadline = segs;
    }

    @Override
    boolean defeat() {
        return  MatchTimeSystem.decMin * 600 +
                MatchTimeSystem.unitMin * 60 +
                MatchTimeSystem.decSec * 10 +
                MatchTimeSystem.unitSec >= deadline;
    }
}
