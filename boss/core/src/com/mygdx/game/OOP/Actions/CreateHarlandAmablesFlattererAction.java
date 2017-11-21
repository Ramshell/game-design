package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Builders.HarlandAmablesFlattererBuilder;
import com.mygdx.game.Builders.HarlandSoldierBuilder;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.SpawnComponent;
import com.mygdx.game.Entities.BuildingEntity;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public class CreateHarlandAmablesFlattererAction extends BuildAction{
    private PlayerComponent player;
    private MapGraph mapGraph;
    private Play play;

    public CreateHarlandAmablesFlattererAction(MapGraph mapGraph, PlayerComponent player, final Play play){
        super(HarlandAmablesFlattererBuilder.BUILD_SPEED, HarlandAmablesFlattererBuilder.MAX_BUILD_SPEED, "Harland Amable's Flatterer");
        this.mapGraph = mapGraph;
        this.player = player;
        this.play = play;
    }

    @Override
    public void act(Entity e) {
        if(e.getClass() != BuildingEntity.class) return;
        SpawnComponent sc = Mappers.spawn.get(e);
        Vector2 v = sc.nextSpawnTile(mapGraph);
        if(v == null){
            player.resources += HarlandAmablesFlattererBuilder.COST;
            return;
        }
        Entity createdEntity = play.amablesFlattererBuilder.getAmablesFlatterer(player, (int)v.x,(int) v.y);
        play.engine.addEntity(createdEntity);
        super.act(createdEntity);
    }
}
