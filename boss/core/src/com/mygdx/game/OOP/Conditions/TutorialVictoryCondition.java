package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

public class TutorialVictoryCondition extends VictoryCondition{
    private final int centers;
    PlayerComponent playerComponent;

    public TutorialVictoryCondition(Engine engine, PlayerComponent playerComponent, int centers) {
        super(engine, "Create " + String.valueOf(centers) + " Harlanding's Center");
        this.playerComponent = playerComponent;
        this.centers = centers;
    }

    @Override
    boolean victory() {
        int count = 0;
        for(Entity e: engine.getEntitiesFor(Family.all(CellsComponent.class, WorldObjectComponent.class,
                PlayerComponent.class).get())){
            if(Mappers.world.get(e).objectName.equals("Harlanding's Center") && playerComponent.equals(Mappers.player.get(e))) count++;
            if(count >= centers) return true;
        }
        return false;
    }
}
