package com.mygdx.game.Builders;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.CellComponent;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Entities.BuildingEntity;

import java.util.Iterator;


public class WallBuilder{
    private TiledMapTileLayer background;
    private TiledMapTileLayer foreground;
    private TiledMapTile wallTile;
    private TiledMapTile wallTile2;
    private TiledMapTile wallTile3;
    private TiledMapTile wallTile4;

    public WallBuilder(OrthogonalTiledMapRenderer renderer){
        this.background = (TiledMapTileLayer)renderer.getMap().getLayers().get("background");
        this.foreground = (TiledMapTileLayer)renderer.getMap().getLayers().get("foreground");
        wallTile = renderer.getMap().getTileSets().getTileSet("ortho_tileset").getTile(196);
        wallTile2 = renderer.getMap().getTileSets().getTileSet("ortho_tileset").getTile(197);
        wallTile3 = renderer.getMap().getTileSets().getTileSet("ortho_tileset").getTile(200);
        wallTile4 = renderer.getMap().getTileSets().getTileSet("ortho_tileset").getTile(201);
    }

    public BuildingEntity getWall(PlayerComponent playerComponent, int x, int y){
        Array<CellComponent> cells = new Array<CellComponent>();
        Vector2 v = new Vector2(x, y);
        CellComponent cellComponent = create_cell(wallTile3, background, v, 0 ,0);
        CellComponent cellComponent2 = create_cell(wallTile4, background, v, 1, 0);
        cellComponent.blocked = true;
        cellComponent2.blocked = true;
        CellComponent cellComponent3 = create_cell(wallTile, foreground, v, 0 ,1);
        CellComponent cellComponent4 = create_cell(wallTile2, foreground, v, 1, 1);
        cells.add(cellComponent);cells.add(cellComponent2);
        cells.add(cellComponent3);cells.add(cellComponent4);
        CellsComponent cellsComponent = new CellsComponent(cells, 2, 2);
        return new BuildingEntity(playerComponent, new Vector2(x, y), cellsComponent);
    }

    private CellComponent create_cell(TiledMapTile t, TiledMapTileLayer layer,
                                       Vector2 position, int xOffset, int yOffset) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(t);
        CellComponent cellComponent = new CellComponent();
        cellComponent.layer = layer;
        cellComponent.position = position.cpy().add(xOffset, yOffset);
        cellComponent.xOffset = xOffset;
        cellComponent.yOffset = yOffset;
        cellComponent.cell = cell;
        return cellComponent;
    }
}
