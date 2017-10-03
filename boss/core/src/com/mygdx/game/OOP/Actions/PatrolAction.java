package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.WorldObjects.PatrolComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;

public class PatrolAction extends Action<Entity>{
    float x, y;

    public PatrolAction(float x, float y){
        this.x = (int) (x / ResourceMapper.tileWidth) * ResourceMapper.tileWidth;
        this.y = (int) (y / ResourceMapper.tileHeight) * ResourceMapper.tileHeight;
    }


    @Override
    public void act(Entity e) {
        Vector2 v = Mappers.world.get(e).bounds.getRectangle().getPosition(new Vector2());
        v.x = (int) (v.x / ResourceMapper.tileWidth) * ResourceMapper.tileWidth;;
        v.y = (int) (v.y / ResourceMapper.tileHeight) * ResourceMapper.tileHeight;;
        e.add(new PatrolComponent(new Vector2(x, y), v));
    }
}
