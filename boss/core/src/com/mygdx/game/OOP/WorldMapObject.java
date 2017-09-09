package com.mygdx.game.OOP;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygdx.game.Mappers.ResourceMapper;

public class WorldMapObject extends RectangleMapObject{

    public WorldMapObject(float x, float y, float width, float height){
        super(x - ResourceMapper.tileWidth / 2, y + ResourceMapper.tileHeight / 2,
                width, height);
    }
}
