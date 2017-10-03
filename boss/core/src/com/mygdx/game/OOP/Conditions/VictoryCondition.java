package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;

public abstract class VictoryCondition implements Condition{
    protected String description;
    protected Engine engine;

    public VictoryCondition(Engine engine, String description){
        this.engine = engine;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean satisfied() {
        return victory();
    }

    abstract boolean victory();
}
