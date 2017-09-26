package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.objects.RectangleMapObject;


public class DynamicWOComponent implements Component{
    public RectangleMapObject lastBounds;
    public float x, y;

    public DynamicWOComponent(RectangleMapObject bounds, float x, float y){
        lastBounds = bounds;
        this.x = x;
        this.y = y;
    }
}
