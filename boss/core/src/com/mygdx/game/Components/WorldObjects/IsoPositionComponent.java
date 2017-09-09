package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class IsoPositionComponent implements Component{
    public Vector2 position;

    public IsoPositionComponent(float x, float y){
        position = new Vector2(x, y);
    }
}
