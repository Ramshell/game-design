package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public Vector2 pos = new Vector2(0.0f, 0.0f);

    public PositionComponent(){
        pos = new Vector2(0.0f, 0.0f);
    }

    public PositionComponent(float x, float y){
        pos = new Vector2(x, y);
    }
}
