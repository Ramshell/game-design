package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Systems.MatchTimeSystem;

public class DeadLineVictoryCondition extends VictoryCondition{
    PlayerComponent playerComponent;
    int deadline, segs;

    public DeadLineVictoryCondition(Engine engine, PlayerComponent playerComponent, int segs) {
        super(engine, createDescription(segs));
        this.segs = segs;
        this.playerComponent = playerComponent;
        deadline = segs;
    }

    private static String createDescription(int segs) {
        return "Complete every mission before " +
                String.valueOf(segs / 60) + (segs / 60 > 1 ? " minutes " : " minute ") +
                (segs % 60 > 0 ? String.valueOf(segs % 60) + (segs % 60 > 1 ? " secs" : " sec") : "");
    }

    @Override
    public String getDescription() {
        int secsPassed = MatchTimeSystem.decMin * 600 +
                MatchTimeSystem.unitMin * 60 +
                MatchTimeSystem.decSec * 10 +
                MatchTimeSystem.unitSec;
        description = createDescription(segs - secsPassed);
        return super.getDescription();
    }

    @Override
    boolean victory() {
        int secsPassed = MatchTimeSystem.decMin * 600 +
                MatchTimeSystem.unitMin * 60 +
                MatchTimeSystem.decSec * 10 +
                MatchTimeSystem.unitSec;
        return  secsPassed < deadline;
    }
}
