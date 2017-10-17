package com.platformer.ar.Components.World;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public Vector2 pos = new Vector2();

    public PositionComponent(float x, float y){
        pos.set(x, y);
    }
}
