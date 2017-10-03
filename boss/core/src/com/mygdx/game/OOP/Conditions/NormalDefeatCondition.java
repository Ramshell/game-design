package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

public class NormalDefeatCondition extends DefeatCondition implements EntityListener{
    PlayerComponent playerComponent;

    public NormalDefeatCondition(Engine engine, PlayerComponent playerComponent) {
        super(engine, "ran out of units and buildings");
        this.playerComponent = playerComponent;
        engine.addEntityListener(Family.all(PlayerComponent.class, WorldObjectComponent.class).get(), this);
    }

    @Override
    boolean defeat() {
        return playerComponent.worldObjects <= 0;
    }

    @Override
    public void entityAdded(Entity entity) {
        if(Mappers.player.get(entity).equals(playerComponent)) playerComponent.worldObjects++;
    }

    @Override
    public void entityRemoved(Entity entity) {
        if(Mappers.player.get(entity).equals(playerComponent)) playerComponent.worldObjects--;
    }
}
