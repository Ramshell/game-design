package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Components.WorldObjects.PatrolComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.MoveAction;

public class PatrolSystem extends IntervalSystem{
    MoveAction m;

    public PatrolSystem() {
        super(0.5f);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        m = new MoveAction(0,0, Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph);

    }

    @Override
    protected void updateInterval() {
        for(Entity e : getEngine().getEntitiesFor(Family.all(PatrolComponent.class).get())){
            if(Mappers.patrolComponentMapper.get(e).fst.epsilonEquals(Mappers.worldPosition.get(e).position,16)){
                m.x = (int) (Mappers.patrolComponentMapper.get(e).snd.x / ResourceMapper.tileWidth);
                m.y = (int) (Mappers.patrolComponentMapper.get(e).snd.y / ResourceMapper.tileHeight);
                m.act(e);
            }else if(Mappers.patrolComponentMapper.get(e).snd.epsilonEquals(Mappers.worldPosition.get(e).position,16)){
                m.x = (int) (Mappers.patrolComponentMapper.get(e).fst.x / ResourceMapper.tileWidth);
                m.y = (int) (Mappers.patrolComponentMapper.get(e).fst.y / ResourceMapper.tileHeight);
                m.act(e);
            }
            System.out.println(Mappers.patrolComponentMapper.get(e).fst.x);
            System.out.println(Mappers.patrolComponentMapper.get(e).fst.y);
            System.out.println(Mappers.patrolComponentMapper.get(e).snd.x);
            System.out.println(Mappers.patrolComponentMapper.get(e).snd.y);
        }
    }
}
