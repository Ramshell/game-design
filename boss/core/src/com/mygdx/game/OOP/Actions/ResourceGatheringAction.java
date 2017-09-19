package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.StartGatheringComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class ResourceGatheringAction extends MoveAction{
    private Engine engine;
    float realX, realY;
    /**
     * x and y should be isoScreen coordinates;
     *
     * @param x
     * @param y
     * @param mapGraph
     **/
    public ResourceGatheringAction(float x, float y, MapGraph mapGraph, Engine engine){
        super(x, y, mapGraph);
        this.engine = engine;
        this.realX = x;
        this.realY = y;
    }

    public void act(Entity worker){
        ImmutableArray<Entity> resources = engine.getEntitiesFor(Family.all(ResourceComponent.class, WorldObjectComponent.class).get());
        for (Entity resource: resources){
            WorldObjectComponent wo = Mappers.world.get(resource);
            if(wo.bounds.getRectangle().contains(realX, realY)){
                super.act(worker);
                worker.add(new StartGatheringComponent(resource, worker));
                break;
            }
        }
    }
}
