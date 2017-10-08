package com.mygdx.game.Entities;

import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
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
import com.mygdx.game.Play;

public class BuildingEntity extends Entity {

    public BuildingEntity(PlayerComponent p, Vector2 pos, CellsComponent cellsComponent,
                          HealthComponent healthComponent, WorldObjectComponent wo, Play play,
                          float visibility){
        SpawnComponent spawnComponent =
                new SpawnComponent((int)pos.x, (int)pos.y, cellsComponent.width, cellsComponent.height);


        add(wo).add(p).add(cellsComponent).add(new RenderableComponent()).add(spawnComponent)
        .add(new WorldPositionComponent(pos.x * ResourceMapper.tileWidth, pos.y * ResourceMapper.tileHeight))
        .add(healthComponent);
    }
}
