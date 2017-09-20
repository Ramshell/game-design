package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.*;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.WorldMapObject;

public class BuildingEntity extends Entity {

    public BuildingEntity(PlayerComponent p, Vector2 pos, CellsComponent cellsComponent,
                          Array<ActionComponent> actions, HealthComponent healthComponent){
        WorldObjectComponent wo = new WorldObjectComponent();
        wo.actions = actions;
        SpawnComponent spawnComponent =
                new SpawnComponent((int)pos.x, (int)pos.y, cellsComponent.width, cellsComponent.height);
        wo.cost = 10;
        wo.objectName = "Harlanding's Center";
        wo.sellValue = 900;
        wo.bounds = new RectangleMapObject(
                pos.x * ResourceMapper.tileWidth,
                pos.y * ResourceMapper.tileHeight,
                ResourceMapper.tileWidth *  cellsComponent.width,
                ResourceMapper.tileHeight * cellsComponent.height);
        add(wo).add(p).add(cellsComponent).add(new RenderableComponent()).add(spawnComponent)
        .add(new WorldPositionComponent(pos.x * ResourceMapper.tileWidth, pos.y * ResourceMapper.tileHeight))
        .add(healthComponent);
    }
}
