package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.ActionComponent;
import com.mygdx.game.Components.WorldObjects.SpawnComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.WorldMapObject;

public class BuildingEntity extends Entity {

    public BuildingEntity(PlayerComponent p, Vector2 pos, CellsComponent cellsComponent,
                          Array<ActionComponent> actions){
        WorldObjectComponent wo = new WorldObjectComponent();
        wo.actions = actions;
        SpawnComponent spawnComponent =
                new SpawnComponent((int)pos.x, (int)pos.y, cellsComponent.width, cellsComponent.height);
        wo.cost = 10;
        wo.hitPoints = 100;
        wo.maxHitPoints = 100;
        wo.objectName = "Wall";
        wo.sellValue = 900;
        wo.bounds = new RectangleMapObject(
                pos.x * ResourceMapper.tileWidth,
                pos.y * ResourceMapper.tileHeight,
                ResourceMapper.tileWidth *  cellsComponent.width,
                ResourceMapper.tileHeight * cellsComponent.height);
        add(wo).add(p).add(cellsComponent).add(new RenderableComponent()).add(spawnComponent)
                .add(new WorldPositionComponent(pos.x * ResourceMapper.tileWidth, pos.y * ResourceMapper.tileHeight));
    }
}
