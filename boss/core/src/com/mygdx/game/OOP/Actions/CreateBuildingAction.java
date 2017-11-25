package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.Buildings.TryingBuildingComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class CreateBuildingAction extends MoveAction{
    Engine engine;
    PlayerComponent player;
    MapGraph mapGraph;
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);
    private int count = 0;

    public CreateBuildingAction(float x, float y, Engine engine, PlayerComponent player, MapGraph mapGraph){
        super(x, y, mapGraph);
        this.player = player;
        this.engine = engine;
        this.mapGraph = mapGraph;
    }

    @Override
    public void act(Entity e) {
        if(count > 0) return;
        TryingBuildingComponent tryingBuildingComponent = Mappers.tryingBuildingComponentMapper.get(e);
        Vector2 v = Mappers.spawn.get(tryingBuildingComponent.building).nextSpawnTile(mapGraph);
        x = (int) v.x;
        y = (int) v.y;
        super.act(e);
        ToBuildComponent bc = new ToBuildComponent();
        bc.building = tryingBuildingComponent.building;
        bc.x = x * ResourceMapper.tileWidth;
        bc.y = y * ResourceMapper.tileHeight;
        e.remove(TryingBuildingComponent.class);
        e.add(bc);
        count++;

    }
}
