package com.mygdx.game.OOP.Conditions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

public class TutorialVictoryCondition extends VictoryCondition{
    public TutorialVictoryCondition(Engine engine) {
        super(engine, "Create 1 Harlanding's Center");
    }

    @Override
    boolean victory() {
        for(Entity e: engine.getEntitiesFor(Family.all(CellsComponent.class, WorldObjectComponent.class).get())){
            if(Mappers.world.get(e).objectName.equals("Harlanding's Center")) return true;
        }
        return false;
    }
}
