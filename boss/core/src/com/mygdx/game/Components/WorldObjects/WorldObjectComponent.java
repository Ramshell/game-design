package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class WorldObjectComponent implements Component{
    public String objectName = "";
    public int cost, sellValue, hitPoints, maxHitPoints;
    public boolean currentlySelected = false;
    public RectangleMapObject bounds;

    public WorldObjectComponent(){}
    public WorldObjectComponent(String name){
        objectName = name;
    }
}
