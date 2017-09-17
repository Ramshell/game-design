package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Components.*;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Systems.BuildingMakingSystem;

public class CreateBuildingAction extends MoveAction{
    Engine engine;
    PlayerComponent player;
    MapGraph mapGraph;
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);

    public CreateBuildingAction(float x, float y, Engine engine, PlayerComponent player, MapGraph mapGraph){
        super(x, y, mapGraph);
        this.player = player;
        this.engine = engine;
    }

    @Override
    public void act(Entity e) {
        super.act(e);
        ToBuildComponent bc = new ToBuildComponent();
        bc.building = player.tryingBuilding;
        e.add(bc);

    }
}
