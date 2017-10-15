package com.platformer.ar.Components.World;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {
    public float x, y;

    public PositionComponent(float x, float y){
        this.x = x;
        this.y = y;
    }
}
