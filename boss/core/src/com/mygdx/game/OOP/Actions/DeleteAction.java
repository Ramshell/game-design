package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Mappers.Mappers;

public class DeleteAction extends Action<Entity>{
    @Override
    public void act(Entity e) {
        if(Mappers.healthComponentMapper.get(e) != null){
            Mappers.healthComponentMapper.get(e).damageTaken = 50000;
        }
    }
}
