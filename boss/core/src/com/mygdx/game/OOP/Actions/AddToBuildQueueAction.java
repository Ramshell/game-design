package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.Mappers;
import javafx.util.Pair;

public class AddToBuildQueueAction extends Action<Entity>{

    public BuildAction action;
    public float cost;

    public AddToBuildQueueAction(BuildAction action, float cost){
        this.action = action;
        this.cost = cost;
    }

    @Override
    public void act(Entity e) {
        PlayerComponent player = Mappers.player.get(e);
        if(player.resources < cost) return;
        player.resources -= cost;
        Mappers.tasksComponentMapper.get(e).createUnit(new Pair<BuildAction, Entity>(action,e));
    }
}
