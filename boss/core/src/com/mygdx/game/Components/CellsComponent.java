package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import javafx.util.Pair;

public class CellsComponent implements Component{
    public Array<CellComponent> cells;

    /** @param cells should  be an array of cellcomponent with one of two layers called background and foreground**/
    public CellsComponent(Array<CellComponent> cells){
        this.cells = cells;
    }

}
