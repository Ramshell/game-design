package com.mygdx.game.Builders;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Entities.BuildingEntity;

import java.util.Iterator;


public class WallBuilder{
    private TiledMapTileLayer background;
    private TiledMapTile wallTile;

    public WallBuilder(OrthogonalTiledMapRenderer renderer){
        this.background = (TiledMapTileLayer)renderer.getMap().getLayers().get("background");
        Iterator<TiledMapTile> iter = renderer.getMap().getTileSets().getTileSet("alien_tileset").iterator();
        while(iter.hasNext()){
            System.out.println(iter.next().getId());
        }
        wallTile = renderer.getMap().getTileSets().getTileSet("alien_tileset").getTile(10);
    }

    public BuildingEntity getWall(PlayerComponent playerComponent, int x, int y){
        return new BuildingEntity(playerComponent, new Vector2(x, y), wallTile, background);
    }
}
