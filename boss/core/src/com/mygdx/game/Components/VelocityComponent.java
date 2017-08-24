package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    public Vector2 pos = new Vector2(0.0f, 0.0f);
    public Vector2 accel = new Vector2(0.0f,0.0f);
}
