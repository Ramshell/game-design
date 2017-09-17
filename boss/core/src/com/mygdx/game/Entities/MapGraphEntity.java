package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class MapGraphEntity extends Entity{
    public MapGraphEntity(MapGraph mapGraph){
        add(new MapGraphComponent(mapGraph));
    }
}
