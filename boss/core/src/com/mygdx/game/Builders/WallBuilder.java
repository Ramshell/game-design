package com.mygdx.game.Builders;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Entities.BuildingEntity;


public class WallBuilder{
    private TiledMapTileLayer background;
    private TiledMapTile wallTile;

    public WallBuilder(IsometricTiledMapRenderer renderer){
        this.background = (TiledMapTileLayer)renderer.getMap().getLayers().get("background");
        wallTile = renderer.getMap().getTileSets().getTileSet("iso-64x64-building").getTile(162);
    }

    public BuildingEntity getWall(PlayerComponent playerComponent, int x, int y){
        return new BuildingEntity(playerComponent, new Vector2(x, y), wallTile, background);
    }
}
