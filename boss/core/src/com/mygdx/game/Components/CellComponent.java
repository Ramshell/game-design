package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class CellComponent implements Component{
    public TiledMapTileLayer.Cell cell;
    public Vector2 position;
    public TiledMapTileLayer layer;
    public int xOffset, yOffset;
    public boolean blocked = false;
}
