package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

public class TutorialVictoryCondition extends VictoryCondition{
    PlayerComponent playerComponent;

    public TutorialVictoryCondition(Engine engine, PlayerComponent playerComponent) {
        super(engine, "Create 1 Harlanding's Center");
        this.playerComponent = playerComponent;
    }

    @Override
    boolean victory() {
        for(Entity e: engine.getEntitiesFor(Family.all(CellsComponent.class, WorldObjectComponent.class,
                PlayerComponent.class).get())){
            if(Mappers.world.get(e).objectName.equals("Harlanding's Center") && playerComponent.equals(Mappers.player.get(e))) return true;
        }
        return false;
    }
}
