package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.*;
import com.mygdx.game.Mappers.ResourceMapper;
import javafx.util.Pair;

public class BuildingEntity extends Entity {
    public BuildingEntity(PlayerComponent p, Vector2 pos, TiledMapTile t,
                          TiledMapTileLayer background){
        WorldObjectComponent wo = new WorldObjectComponent();
        wo.cost = 10;
        wo.hitPoints = 100;
        wo.maxHitPoints = 100;
        wo.objectName = "Wall";
        wo.sellValue = 900;
        wo.bounds = new RectangleMapObject(
                pos.x * ResourceMapper.tileWidh,
                pos.y * ResourceMapper.tileHeight,
                ResourceMapper.tileWidh,
                ResourceMapper.tileHeight);
        Array<CellComponent> cells = new Array<CellComponent>();
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(t);
        CellComponent cellComponent = new CellComponent();
        cellComponent.layer = background;
        cellComponent.position = pos;
        cellComponent.cell = cell;
        cells.add(cellComponent);
        CellsComponent c = new CellsComponent(cells);
        add(wo).add(p).add(c).add(new RenderableComponent());
    }
}
