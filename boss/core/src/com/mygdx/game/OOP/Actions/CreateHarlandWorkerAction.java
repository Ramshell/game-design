package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Builders.HarlandWorkerBuilder;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.WorldObjects.SpawnComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Entities.BuildingEntity;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public class CreateHarlandWorkerAction extends BuildAction{
    private PlayerComponent player;
    private MapGraph mapGraph;
    private Play play;

    public CreateHarlandWorkerAction(MapGraph mapGraph, PlayerComponent player, final Play play){
        super(HarlandWorkerBuilder.BUILD_SPEED, HarlandWorkerBuilder.MAX_BUILD_SPEED, "Harland Worker");
        this.mapGraph = mapGraph;
        this.player = player;
        this.play = play;
    }

    @Override
    public void act(Entity e) {
        if(e.getClass() != BuildingEntity.class) return;
        SpawnComponent sc = Mappers.spawn.get(e);
        WorldObjectComponent wo = Mappers.world.get(e);
        Vector2 v = sc.nextSpawnTile(mapGraph);
        if(v == null){
            player.resources += HarlandWorkerBuilder.COST;
            return;
        }
        Entity createdEntity = play.workerBuilder.getWorker(player, (int)v.x,(int) v.y);
        play.engine.addEntity(createdEntity);
        super.act(createdEntity);
    }
}
