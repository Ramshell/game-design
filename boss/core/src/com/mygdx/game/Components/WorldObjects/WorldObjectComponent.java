package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.OOP.Actions.Action;

public class WorldObjectComponent implements Component{
    public String objectName = "";
    public int cost, sellValue;
    public RectangleMapObject bounds;
    public Array<ActionComponent> actions = new Array<ActionComponent>();

    public WorldObjectComponent(){}
    public WorldObjectComponent(String name){
        objectName = name;
    }
}
