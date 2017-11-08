package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.Mappers;
import javafx.util.Pair;

public class AddToBuildQueueAction extends Action<Entity>{

    public Action<Entity> action;

    public AddToBuildQueueAction(Action<Entity> action){
        this.action = action;
    }

    @Override
    public void act(Entity e) {
        Mappers.tasksComponentMapper.get(e).createUnit(new Pair<Action<Entity>, Entity>(action,e));
    }
}
