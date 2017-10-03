package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Systems.MatchTimeSystem;

public class DeadLineVictoryCondition extends VictoryCondition{
    PlayerComponent playerComponent;
    int deadline;

    public DeadLineVictoryCondition(Engine engine, PlayerComponent playerComponent, int segs) {
        super(engine, "Complete every mission before " +
                String.valueOf(segs / 60) + (segs / 60 > 1 ? " minutes " : " minute ") +
                (segs % 60 > 0 ? String.valueOf(segs % 60) + (segs % 60 > 1 ? " secs" : " sec") : "")
        );
        this.playerComponent = playerComponent;
        deadline = segs;
    }

    @Override
    boolean victory() {
        return  MatchTimeSystem.decMin * 600 +
                MatchTimeSystem.unitMin * 60 +
                MatchTimeSystem.decSec * 10 +
                MatchTimeSystem.unitSec < deadline;
    }
}
