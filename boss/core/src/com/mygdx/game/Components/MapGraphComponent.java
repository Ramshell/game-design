package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class MapGraphComponent implements Component{
    public MapGraph mapGraph;

    public MapGraphComponent(MapGraph mapGraph){
        this.mapGraph = mapGraph;
    }
}
