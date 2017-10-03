package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;

public abstract class DefeatCondition implements Condition{
    protected String description;
    protected Engine engine;

    public DefeatCondition(Engine engine, String description){
        this.engine = engine;
        this.description = "You lose if you: " + description;
    }


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean satisfied() {
        return defeat();
    }

    abstract boolean defeat();
}
