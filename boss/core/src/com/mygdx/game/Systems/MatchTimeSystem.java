package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Mappers.Mappers;

public class MatchTimeSystem extends IntervalSystem{
    private HUDComponent hudComponent;
    public static Integer unitSec, decSec, unitMin, decMin;

    public MatchTimeSystem() {
        super(1);
        unitMin = 0;
        unitSec = 0;
        decSec = 0;
        decMin = 0;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        hudComponent = Mappers.hud.get(engine.getEntitiesFor(Family.all(HUDComponent.class).get()).first());
    }

    @Override
    protected void updateInterval() {
        unitSec = (unitSec + 1) % 10;
        if(unitSec == 0) decSec = (decSec + 1) % 6;
        if(unitSec == 0 && decSec == 0) unitMin = (unitMin + 1) % 10;
        if(unitSec == 0 && decSec == 0 && unitMin == 0) decMin = (decMin + 1) % 6;
        hudComponent.matchTime.setText(
                decMin.toString() + unitMin.toString() +
                        ":" +
                decSec.toString() + unitSec.toString());
    }
}
