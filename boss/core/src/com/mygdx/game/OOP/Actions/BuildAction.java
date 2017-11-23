package com.mygdx.game.OOP.Actions;


import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.WorldObjects.PlaySoundComponent;

public abstract class BuildAction extends Action<Entity>{
    protected float buildSpeed, maxBuildSpeed;
    protected String name;

    public BuildAction(float bs, float mbs, String n){
        buildSpeed = bs;
        maxBuildSpeed = mbs;
        name = n;
    }

    @Override
    public void act(Entity e) {
        e.add(new PlaySoundComponent("spawn"));
    }

    public float getBuildSpeed() {
        return buildSpeed;
    }

    public float getMaxBuildSpeed() {
        return maxBuildSpeed;
    }

    public String getName() {
        return name;
    }
}
